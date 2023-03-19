package de.holtmeyer.niklas.spotify.data.service.configuration;

import de.holtmeyer.niklas.spotify.data.entity.dto.Track;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Endpoint {
    public static AuthEndpoint auth = new AuthEndpoint();
    public static UserEndpoint user = new UserEndpoint();
    public static PlaylistEndpoint playlist = new PlaylistEndpoint();
    public static TrackEndpoint endpoint = new TrackEndpoint();
    public static SearchEndpoint search = new SearchEndpoint();
}
