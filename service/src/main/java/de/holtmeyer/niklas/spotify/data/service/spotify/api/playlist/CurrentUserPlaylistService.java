package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserPlaylistService {
    @Autowired PlaylistAPI api;

    public Optional<PlaylistsWithMinimalTrackInfo> findByName(String playlistName) {
        return this.api.getCurrentUserPlaylists()
                .getBody()
                .get()
                .stream()
                .filter(x->x.getName().contains(playlistName))
                .findFirst();
    }
}
