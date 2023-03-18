package de.holtmeyer.niklas.spotify.data.service.authorization.request;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.response.RequestAccessTokenResponse;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPostRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.ClientConfiguration;
import de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenRequest{
    String code;
    String redirectURI;
    String grantType;
    ClientConfiguration clientConfiguration;
    String contentType;
    public AccessTokenRequest(String code, ClientConfiguration clientConfiguration) {
        this.code = code;
        this.redirectURI = clientConfiguration.getREDIRECT_URI();
        this.grantType = "authorization_code";
        this.clientConfiguration = clientConfiguration;
        this.contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
    }

    public Response<RequestAccessTokenResponse> execute(){
        Map<String, Object> payload = new HashMap<>();

        payload.put("code", this.code);
        payload.put("redirect_uri", this.redirectURI);
        payload.put("grant_type", this.grantType);

        return SpotifyPostRequest.<RequestAccessTokenResponse>builder()
                .url(Endpoint.auth.REQUEST_ACCESS_TOKEN)
                .responseClass(RequestAccessTokenResponse.class)
                .contentType(this.contentType)
                .clientConfiguration(this.clientConfiguration)
                .payload(payload)
                .build()
                .addAuthorizationHeaderBearerSpotify().execute();
    }
}
