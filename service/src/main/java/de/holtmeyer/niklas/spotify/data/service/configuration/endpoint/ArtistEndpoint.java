package de.holtmeyer.niklas.spotify.data.service.configuration.endpoint;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArtistEndpoint {
    public static final String BY_ID = "https://api.spotify.com/v1/artists/%s";
    public static final String BY_QUERY = "https://api.spotify.com/v1/artists";

    public static final String ALBUMS_BY_ID = "https://api.spotify.com/v1/artists/%s/albums";
    public static final String TOP_TRACKS_BY_ID = "https://api.spotify.com/v1/artists/%s/top-tracks";
    public static final String RELATED_ARTISTS_BY_ID = "https://api.spotify.com/v1/artists/%s/related-artists";
}
