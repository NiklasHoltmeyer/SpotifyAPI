package de.holtmeyer.niklas.spotify.data.service.configuration.endpoint;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserEndpoint {
    public final String BY_ID = "https://api.spotify.com/v1/users/%s";
    public static final UserCurrentEndpoint current = new UserCurrentEndpoint();
}
