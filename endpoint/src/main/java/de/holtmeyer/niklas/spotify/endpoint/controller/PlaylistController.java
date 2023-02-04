package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Playlist;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.spotify.playlist.PlaylistService;
import de.holtmeyer.niklas.spotify.endpoint.api.PlaylistAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PlaylistController implements PlaylistAPI {
    @Autowired
    PlaylistService playlistService;

    public Response<? extends Playlist> get(@PathVariable String playlist_id) {
        return playlistService.get(playlist_id);
    }
}
