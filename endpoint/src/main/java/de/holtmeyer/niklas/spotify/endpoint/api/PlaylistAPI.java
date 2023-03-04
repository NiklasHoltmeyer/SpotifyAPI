package de.holtmeyer.niklas.spotify.endpoint.api;

import de.holtmeyer.niklas.spotify.data.entity.dto.PlaylistDetails;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping(path = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PlaylistAPI {
    // https://developer.spotify.com/documentation/web-api/reference/#/operations/get-playlist
    @GetMapping("/{playlist_id}")
    Response get(@PathVariable String playlist_id);

    @GetMapping("/{user_id}/playlists")
    Response getUserPlaylists(@PathVariable String user_id);

    @PutMapping("/{playlist_id}")
    Response putDetails(@RequestBody PlaylistDetails playlistDetails);

    @GetMapping("/items/{playlist_id}")
    Response getItems(@PathVariable String playlist_id);

    @PutMapping("/items/{playlist_id}")
    Response updateItems(@PathVariable String playlist_id);

    @DeleteMapping("/items/{playlist_id}")
    Response deleteItems(@PathVariable String playlist_id);

    @PostMapping("/{user_id}/playlist")
    Response create(@PathVariable String playlist_id, @RequestBody PlaylistDetails playlistDetails);

    @GetMapping("/browse/featured-playlists")
    Response getFeatured();

    @GetMapping("/browse/categories/{category_id}/playlists")
    Response getByCategory(@PathVariable String category_id);

    @GetMapping("/{playlist_id}/images")
    Response getCoverImage(@PathVariable String playlist_id);

    @PutMapping("/{playlist_id}/images")
    Response putCoverImage(@PathVariable String playlist_id);

}
