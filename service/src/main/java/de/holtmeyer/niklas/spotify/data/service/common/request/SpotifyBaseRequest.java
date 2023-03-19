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
import lombok.experimental.SuperBuilder;

import java.util.Base64;

@Getter
@SuperBuilder
public abstract class SpotifyBaseRequest<ResponseType, HttpRequestType> extends Request<ResponseType> {
    AccessToken apiToken;
    ClientConfiguration clientConfiguration;

    public SpotifyBaseRequest addAuthorizationHeaderBearerSpotify(){
        final String clientIDSecret = clientConfiguration.getID() + ":" + clientConfiguration.getSECRET();
        final String basicAuthToken = Base64.getEncoder().encodeToString(clientIDSecret.getBytes());

        this.addAuthorizationHeaderBasic(basicAuthToken);
        return this;
    }

    abstract HttpRequest<? extends HttpRequestType> buildHttpRequest();

    public Response<? extends ResponseType> execute(){
        var httpResponse = this.buildHttpRequest().asObject(this.responseClass);
        return new Response<>(httpResponse);
    }

    /**
     * Load all Pages.
     */
    public ResponseList<? extends ResponseType> executeAll(){
        var httpPageResponse = this.buildHttpRequest()
                .asPaged(this::asObject, this::nextPage);
        return new ResponseList<>(httpPageResponse);
    }

    public Response<? extends Object> execute(ResponseMapper mapper){
        var jsonResponse = this.buildHttpRequest().asJson();
        var mappedResponse = mapper.map(jsonResponse);

        return new Response<Object>(new Response<JsonNode>(jsonResponse), mappedResponse);
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
}
