package de.holtmeyer.niklas.spotify.data.service.common.request;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import kong.unirest.HttpRequest;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
@SuperBuilder
public class SpotifyPutRequest<ResponseType> extends SpotifyBaseRequest<ResponseType, MultipartBody> {
    Map<String, Object> payload;

    @Override
    HttpRequest<? extends MultipartBody> buildHttpRequest() {
        var httpRequest = Unirest.put(this.url);
        if(this.headers != null && !this.headers.isEmpty()){
            httpRequest = httpRequest.headers(this.headers);
        }else{
            this.addAuthorizationHeaderBearer(apiToken.getAccessToken());
        }

        if(this.queryParameter != null && !this.queryParameter.isEmpty()){
            httpRequest = httpRequest.queryString(this.queryParameter);
        }

        if(this.payload == null){
            this.payload = new HashMap<>();
        }

        return httpRequest.fields(this.payload);
    }

    @Override
    public Response<? extends ResponseType> execute(){
        var httpResponse = this.buildHttpRequest().asJson();
        return null;
    }
}
