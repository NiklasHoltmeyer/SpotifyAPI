package de.holtmeyer.niklas.spotify.data.service.common.request;

import kong.unirest.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
@SuperBuilder
public class SpotifyPostRequest<ResponseType> extends SpotifyBaseRequest<ResponseType, MultipartBody> {
    Map<String, Object> payload;
    Object body;

    @Override
    HttpRequest buildHttpRequest() {
        var httpRequest = Unirest.post(this.url);
        if(this.headers == null || this.headers.isEmpty()){
            this.addAuthorizationHeaderBearer(apiToken.getAccessToken());
        }

        httpRequest = httpRequest.headers(this.headers);

        if(this.queryParameter != null && !this.queryParameter.isEmpty()){
            httpRequest = httpRequest.queryString(this.queryParameter);
        }

        if(this.payload == null){
            this.payload = new HashMap<>();
        }

        if(this.body != null){
            return httpRequest.body(this.body);
        }

        return httpRequest.fields(this.payload);
    }
}
