package de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper;

import de.holtmeyer.niklas.spotify.data.entity.dto.Owner;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;

import java.util.Optional;

public class PlaylistMapper {
    public static Owner getOwner(PlaylistsWithMinimalTrackInfo playlist){
        return Optional.ofNullable(playlist).map(BasePlaylist::getOwner).orElse(null);
    }
}
