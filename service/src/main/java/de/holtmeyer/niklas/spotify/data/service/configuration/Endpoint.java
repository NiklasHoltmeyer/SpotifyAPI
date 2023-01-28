package de.holtmeyer.niklas.spotify.data.service.configuration;

public class Endpoint {
    public static final String REQUEST_AUTH_CODE = "https://accounts.spotify.com/authorize";
    public static final String REQUEST_ACCESS_TOKEN =  "https://accounts.spotify.com/api/token";

    public static final String USERS_CURRENT_USERS_PROFILE =  "https://api.spotify.com/v1/me";
    public static final String USERS_CURRENT_USERS_TOP_ARTISTS = "https://api.spotify.com/v1/me/top/artists";
    public static final String USERS_CURRENT_USERS_TOP_TRACKS = "https://api.spotify.com/v1/me/top/tracks";
    public static final String USERS_CURRENT_USERS_FOLLOWED_ARTISTS = "https://api.spotify.com/v1/me/following?type=artist";
    public static final String USERS_CURRENT_CHECK_IF_USER_FOLLOWS_ARTISTS_OR_USERS = "https://api.spotify.com/v1/me/following/contains";
    public static final String USERS_USER_IDS_PROFILE = "https://api.spotify.com/v1/users/%s";
    public static final String USERS_USER_FOLLOW_PLAYLIST = "https://api.spotify.com/v1/playlists/%s/followers";

    public static final String PLAYLIST_BY_ID = "https://api.spotify.com/v1/playlists/%s";
}
