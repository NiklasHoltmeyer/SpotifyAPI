package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.strategy;

public record PlaylistListStrategy(
    // All Playlists of Users Current User Follows (includes Playlist::Owner Current User Follows)
    boolean userPlaylistsCurrentUserFollows,
    boolean savedSongs,
    boolean currentUserPlaylists
)
{}
