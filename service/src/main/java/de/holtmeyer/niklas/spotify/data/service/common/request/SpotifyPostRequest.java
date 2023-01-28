package de.holtmeyer.niklas.spotify.data.service.common.request;

import kong.unirest.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class SpotifyPostRequest<ResponseType> extends SpotifyBaseRequest<ResponseType, MultipartBody> {
    @NonNull
    Map<String, Object> payload;

    @Override
    MultipartBody buildHttpRequest() {
        var httpRequest = Unirest.post(this.url);
        if(this.headers != null && !this.headers.isEmpty()){
            httpRequest = httpRequest.headers(this.headers);
        }else{
            this.addAuthorizationHeaderBasic(apiToken.getAccessToken());
        }

        if(this.queryParameter != null && !this.queryParameter.isEmpty()){
            httpRequest = httpRequest.queryString(this.queryParameter);
        }

        return httpRequest.fields(this.payload);
    }
}
