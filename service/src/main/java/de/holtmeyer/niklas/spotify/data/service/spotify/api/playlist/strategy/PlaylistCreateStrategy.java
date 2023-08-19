package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.strategy;

import org.springframework.lang.Nullable;

public record PlaylistCreateStrategy(
    String playlistName,
    // Create Playlist, if Playlist not Existing.
    boolean createIfNotExist,
    boolean deleteTracks,

    @Nullable
    String description
){
    public PlaylistCreateStrategy(String playlistName, boolean createIfNotExist, boolean deleteTracks) {
        this(playlistName, createIfNotExist, deleteTracks, null);
    }
}