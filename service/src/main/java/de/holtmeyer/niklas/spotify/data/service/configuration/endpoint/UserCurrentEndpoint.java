package de.holtmeyer.niklas.spotify.data.service.configuration.endpoint;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserCurrentEndpoint {
    public static final String ME =  "https://api.spotify.com/v1/me";
    public static final String TOP_ARTISTS = "https://api.spotify.com/v1/me/top/artists";
    public static final String TOP_TRACKS = "https://api.spotify.com/v1/me/top/tracks";
    public static final String FOLLOWED_ARTISTS = "https://api.spotify.com/v1/me/following?type=artist";
    public final String FOLLOW_PLAYLIST = "https://api.spotify.com/v1/playlists/%s/followers";
    public static final String FOLLOWS_ARTISTS_OR_USERS = "https://api.spotify.com/v1/me/following/contains";
}
