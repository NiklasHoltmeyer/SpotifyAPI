package de.holtmeyer.niklas.spotify.data.service.configuration;

import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.AuthEndpoint;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.PlaylistEndpoint;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.SearchEndpoint;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.UserEndpoint;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Endpoint {
    public static AuthEndpoint auth = new AuthEndpoint();
    public static UserEndpoint user = new UserEndpoint();
    public static PlaylistEndpoint playlist = new PlaylistEndpoint();
    public static SearchEndpoint search = new SearchEndpoint();
}
