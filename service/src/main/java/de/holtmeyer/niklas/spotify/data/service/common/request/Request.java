package de.holtmeyer.niklas.spotify.data.service.common.request;

import kong.unirest.HttpRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Getter @AllArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder
public abstract class Request<Response> {
    String url;
    Class<? extends Response>  responseClass;
    @Builder.Default
    String contentType = MediaType.APPLICATION_JSON_VALUE;
    Map<String, String> headers;
    Map<String, Object> queryParameter;


    protected void addHeader(String key, String value){
        if(this.headers == null){
            this.headers = new HashMap<>();
        }

        this.headers.put(key, value);
    }

    protected void addAuthorizationHeaderBearer(String bearerToken){
        final String Authorization = "Authorization: Bearer " + bearerToken;

        addHeader("Authorization", Authorization);
        addHeader("Content-Type", this.contentType);
    }

    protected void addAuthorizationHeaderBasic(String basicToken){
        if(this.headers == null){
            headers = new HashMap<>();
        }
        final String Authorization = "Basic " + basicToken;

        addHeader("Authorization", Authorization);
        addHeader("Content-Type", this.contentType);
    }
}
