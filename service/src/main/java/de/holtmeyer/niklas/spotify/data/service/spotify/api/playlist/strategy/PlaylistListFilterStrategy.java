package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.strategy;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

public record PlaylistListFilterStrategy(
    List<Predicate<PlaylistsWithMinimalTrackInfo>> playlistFilter,
    Predicate<PlaylistTrack> playlistTrackFilter
){
}