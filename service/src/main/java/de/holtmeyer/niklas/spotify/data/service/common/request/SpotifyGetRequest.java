package de.holtmeyer.niklas.spotify.data.service.common.request;

import kong.unirest.GetRequest;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SpotifyGetRequest<ResponseType> extends SpotifyBaseRequest<ResponseType, GetRequest> {
    @Override
    protected GetRequest buildHttpRequest(){
        GetRequest httpRequest = Unirest.get(this.url);

        if(this.headers == null || this.headers.isEmpty()){
            this.addAuthorizationHeaderBearer(apiToken.getAccessToken());
        }

        httpRequest = httpRequest.headers(this.headers);

        if(this.queryParameter != null && !this.queryParameter.isEmpty()){
            httpRequest = httpRequest.queryString(this.queryParameter);
        }

        return httpRequest;
    }
}
