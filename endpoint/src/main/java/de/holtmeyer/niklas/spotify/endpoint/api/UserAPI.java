package de.holtmeyer.niklas.spotify.endpoint.api;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserAPI<T> {
    // https://developer.spotify.com/documentation/web-api/reference/#/operations/get-current-users-profile
    @GetMapping("/{user_id}")
    Response get(@PathVariable String user_id);
    @GetMapping("/me")
    Response getCurrent();
    @GetMapping("/me/artists")
    Response getCurrentUsersTopArtists(@RequestParam String limit, @RequestParam String offset);
    @GetMapping("/me/artists/following")
    Response getCurrentUsersFollowedArtists(@RequestParam String limit, @RequestParam String offset);
    @GetMapping("/me/artists/following/{id}")
    Response isCurrentUsersFollowedArtists(@PathVariable String id);
    @GetMapping("/me/artists/following/")
    Response isCurrentUsersFollowedArtists(@RequestBody List<String> ids);
    @GetMapping("/me/users/following/{id}")
    Response isCurrentUsersFollowedUsers(@PathVariable String id);
    @GetMapping("/me/users/following/")
    Response isCurrentUsersFollowedUsers(@RequestBody List<String> ids);
    @PutMapping("/me/playlist/{id}")
    Response followPlaylist(@PathVariable String id);
    @DeleteMapping("/me/playlist/{id}")
    Response unfollowPlaylist(@PathVariable String id);
    @PutMapping("/me/artists")
    Response followArtists(@RequestBody List<String> ids);
    @PutMapping("/me/users")
    Response followUsers(@RequestBody List<String> ids);
    @DeleteMapping("/me/artists")
    Response unfollowArtists(@RequestBody List<String> ids);
    @DeleteMapping("/me/users")
    Response unfollowUsers(@RequestBody List<String> ids);
    @GetMapping("/demo/{id}")
    Response demo(@PathVariable String id);
}