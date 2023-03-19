package de.holtmeyer.niklas.spotify.data.service.configuration.endpoint;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaylistEndpoint {
    public static final String BY_ID = "https://api.spotify.com/v1/playlists/%s";
    public static final String BY_USER_ID = "https://api.spotify.com/v1/users/%s/playlists";

    public static final String TRACKS_BY_ID = "https://api.spotify.com/v1/playlists/%s/tracks";
    public static final String ME = "https://api.spotify.com/v1/me/playlists";

    public static final String UPDATE_TRACKS = "https://api.spotify.com/v1/playlists/%s/tracks";
}
