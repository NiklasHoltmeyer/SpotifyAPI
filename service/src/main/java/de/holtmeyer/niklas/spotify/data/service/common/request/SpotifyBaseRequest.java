package de.holtmeyer.niklas.spotify.data.service.common.request;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.ResponseList;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.configuration.ClientConfiguration;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;

import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@SuperBuilder
@Log4j2
public abstract class SpotifyBaseRequest<ResponseType, HttpRequestType> extends Request<ResponseType> {
    AccessToken apiToken;
    ClientConfiguration clientConfiguration;
    private static final int RETRIES = 5; // Number of Retries per Request

    public SpotifyBaseRequest addAuthorizationHeaderBearerSpotify(){
        final String clientIDSecret = clientConfiguration.getID() + ":" + clientConfiguration.getSECRET();
        final String basicAuthToken = Base64.getEncoder().encodeToString(clientIDSecret.getBytes());

        this.addAuthorizationHeaderBasic(basicAuthToken);
        return this;
    }

    abstract HttpRequest<? extends HttpRequestType> buildHttpRequest();


    public Response<? extends ResponseType> execute(){
        return this.execute(0);
    }

    private Response<? extends ResponseType> execute(int retry){
        var httpResponse = this.buildHttpRequest().asObject(this.responseClass);
        if(this.validResponse(httpResponse)){
            return new Response<>(httpResponse);
        }

        if(retry <= RETRIES){
            sleepRetry(retry);
            return this.execute((retry+1));
        }

        throw this.requestFailedException(httpResponse);
    }

    /**
     * Load all Pages.
     */
    public ResponseList<? extends ResponseType> executeAll(){
        return this.executeAll(0);
    }

    private ResponseList<? extends ResponseType> executeAll(int retry){
        var httpPageResponse = this.buildHttpRequest()
                .asPaged(this::asObject, this::nextPage);
        var response = new ResponseList<>(httpPageResponse);

        var error = httpPageResponse.stream().filter(Predicate.not(this::validResponse)).findFirst().orElse(null);

        if(error == null){
            return response;
        }

        if(retry <= RETRIES){
            sleepRetry(retry);
            return this.executeAll((retry+1));
        }

        throw this.requestFailedException(error);
    }

    private boolean validResponse(HttpResponse response) {
        return response.getStatus() >= 200 || response.getStatus() < 300;
    }

    private RuntimeException requestFailedException(HttpResponse x) {
        var msg = "URL: %s - Error: %s".formatted(this.getUrl(), x.getStatusText());
        return new RuntimeException(msg);
    }

    public Response<? extends Object> execute(ResponseMapper mapper){
        return this.execute(mapper);
    }

    private Response<? extends Object> execute(ResponseMapper mapper, int retry){
        var jsonResponse = this.buildHttpRequest().asJson();
        var mappedResponse = mapper.map(jsonResponse);


        if(this.validResponse(jsonResponse)){
            return new Response<Object>(new Response<JsonNode>(jsonResponse), mappedResponse);
        }

        if(retry <= RETRIES){
            sleepRetry(retry);
            return this.execute(mapper, (retry+1));
        }

        throw this.requestFailedException(jsonResponse);
    }

    HttpResponse<ResponseType> asObject(HttpRequest response){
        return response.asObject(this.responseClass); //Function<HttpResponse<T>, String> linkExtractor
    }

    String nextPage(HttpResponse<ResponseType> response){
        if(response.getBody() instanceof Pageable body){
            return body.getNext();
        }
        return "";
    }

    @SneakyThrows
    void sleepRetry(final int retry){
        var msg = String.format("%s: Waiting %d seconds", this.getUrl(), retry);
        log.warn(msg);
        TimeUnit.SECONDS.sleep(retry);
    }
}
