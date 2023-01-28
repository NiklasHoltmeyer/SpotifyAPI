package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Playlist;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.spotify.playlist.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlaylistController {
    @Autowired
    PlaylistService playlistService;

    @GetMapping("/{playlist_id}")
    public ResponseEntity<? extends Playlist> getPlaylist(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                                                    @PathVariable String playlist_id) {
        playlistService.getAccessToken().setAccessToken(accesstoken.get());
        return ResponseEntity.ok(playlistService.get(playlist_id).getBody().get());
    }
}
