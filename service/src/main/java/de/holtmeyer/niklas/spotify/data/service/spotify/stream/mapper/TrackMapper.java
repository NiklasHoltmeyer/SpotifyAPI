package de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper;

import de.holtmeyer.niklas.spotify.data.entity.dto.Album;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;

import java.util.Optional;

public class TrackMapper {
    public static String getUri(UserSavedTrack track){
        return Optional.ofNullable(track)
                .map(UserSavedTrack::getTrack)
                .map(HasHref::getUri)
                .orElse(null);
    }

    public static String getReleaseDate(PlaylistTrack track){
        return Optional.ofNullable(track).map(PlaylistTrack::getAlbum).map(Album::getRelease_date).orElse(null);
    }
}
