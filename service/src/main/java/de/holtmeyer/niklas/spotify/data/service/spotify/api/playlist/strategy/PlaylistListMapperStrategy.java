package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.strategy;

public record PlaylistListMapperStrategy(
    boolean playlistsDistinct,
    boolean tracksDistinct,
    boolean mutable
){}
