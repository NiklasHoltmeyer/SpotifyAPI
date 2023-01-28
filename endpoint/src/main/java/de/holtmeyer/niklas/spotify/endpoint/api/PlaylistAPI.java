package de.holtmeyer.niklas.spotify.endpoint.api;

import de.holtmeyer.niklas.spotify.data.entity.dto.Playlist;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Validated
@RequestMapping(path = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PlaylistAPI {
    @GetMapping("{playlist_id}")
    ResponseEntity<? extends Playlist> get(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                           @PathVariable String playlist_id);
}
