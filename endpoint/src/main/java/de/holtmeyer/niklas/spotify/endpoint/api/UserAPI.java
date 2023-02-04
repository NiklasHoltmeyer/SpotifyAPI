package de.holtmeyer.niklas.spotify.endpoint.api;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserAPI<T> {
    @GetMapping("/{user_id}")
    Response get(@PathVariable String user_id);
    @GetMapping("/me")
    Response getCurrent();
    @GetMapping("/me/artists")
    Response getCurrentUsersTopArtists(@RequestParam String limit, @RequestParam String offset);
    @GetMapping("/me/artists/following")
    Response getCurrentUsersFollowedArtists(@RequestParam String limit, @RequestParam String offset);
    @GetMapping("/me/artists/following/{ids}")
    Response isCurrentUsersFollowedArtists(@PathVariable String ids);
    @GetMapping("/me/artists/following/")
    Response isCurrentUsersFollowedArtists(@RequestBody List<String> ids);
    @GetMapping("/me/users/following/{ids}")
    Response isCurrentUsersFollowedUsers(@PathVariable String ids);
    @GetMapping("/me/users/following/")
    Response isCurrentUsersFollowedUsers(@RequestBody List<String> ids);
}