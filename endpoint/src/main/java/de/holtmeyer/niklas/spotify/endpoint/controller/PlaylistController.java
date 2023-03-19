package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Playlist;
import de.holtmeyer.niklas.spotify.data.entity.dto.PlaylistDetails;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.spotify.playlist.PlaylistService;
import de.holtmeyer.niklas.spotify.endpoint.api.PlaylistAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PlaylistController implements PlaylistAPI {
    @Autowired
    PlaylistService playlistService;

    public Response<? extends Playlist> get(@PathVariable String playlist_id) {
        return playlistService.get(playlist_id);
    }

    @Override
    public Response getUserPlaylists(String user_id) {
        return null;
    }

    @Override
    public Response getCurrentUserPlaylists() {
        return playlistService.getCurrentUserPlaylists();
    }

    @Override
    public Response putDetails(PlaylistDetails playlistDetails) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response getItems(String playlist_id) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response updateItems(String playlist_id) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response deleteItems(String playlist_id) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response create(String playlist_id, PlaylistDetails playlistDetails) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response getFeatured() {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response getByCategory(String category_id) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response getCoverImage(String playlist_id) {
        throw new RuntimeException("Unsuported - TODO");
    }

    @Override
    public Response putCoverImage(String playlist_id) {
        throw new RuntimeException("Unsuported - TODO");
    }
}
