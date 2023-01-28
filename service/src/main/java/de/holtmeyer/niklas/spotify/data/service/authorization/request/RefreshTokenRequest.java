package de.holtmeyer.niklas.spotify.data.service.authorization.request;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.response.RequestAccessTokenResponse;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPostRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.ClientConfiguration;
import de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint;
import kong.unirest.HttpResponse;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class RefreshTokenRequest{
    String refreshToken;
    String redirectURI;
    String grantType;
    ClientConfiguration clientConfiguration;
    String contentType;
    public RefreshTokenRequest(String refresh_token, ClientConfiguration clientConfiguration) {
        // MediaType.APPLICATION_FORM_URLENCODED_VALUE
        this.refreshToken = refresh_token;
        this.redirectURI = clientConfiguration.getREDIRECT_URI();
        this.grantType = "refresh_token";
        this.clientConfiguration = clientConfiguration;
        this.contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
    }

    public Response<RequestAccessTokenResponse> execute(){
        Map<String, Object> payload = new HashMap<>();

        payload.put("refresh_token", this.refreshToken);
        payload.put("grant_type", this.grantType);

        return SpotifyPostRequest.<RequestAccessTokenResponse>builder()
                .url(Endpoint.REQUEST_ACCESS_TOKEN)
                .responseClass(RequestAccessTokenResponse.class)
                .contentType(this.contentType)
                .clientConfiguration(this.clientConfiguration)
                .payload(payload)
                .build()
                .addAuthorizationHeaderBearerSpotify()
                .execute();
    }
}
