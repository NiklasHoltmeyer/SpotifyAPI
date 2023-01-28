package de.holtmeyer.niklas.spotify.data.service.authorization.response;

import lombok.Getter;

@Getter
public class RequestAccessTokenResponse {
    // Authorization - Step 2 - Response Body

    /**
     * 	An Access Token that can be provided in subsequent calls, for example to Spotify Web API services.
     */
    String access_token;

    /**
     * How the Access Token may be used: always “Bearer”.
     */
    String token_type;

    /**
     * A space-separated list of scopes which have been granted for this access_token
     */
    String scope;

    /**
     * The time period (in seconds) for which the Access Token is valid.
     */
    Integer expires_in;

    /**
     *	A token that can be sent to the Spotify Accounts service in place of an authorization code. (When the access code expires, send a POST request to the Accounts service /api/token endpoint, but use this code in place of an authorization code. A new Access Token will be returned. A new refresh token might be returned too.)
     */
    String refresh_token;
}