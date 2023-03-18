package de.holtmeyer.niklas.spotify.data.service.configuration.endpoint;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthEndpoint {
        public final String REQUEST_AUTH_CODE = "https://accounts.spotify.com/authorize";
        public final String REQUEST_ACCESS_TOKEN =  "https://accounts.spotify.com/api/token";
}
