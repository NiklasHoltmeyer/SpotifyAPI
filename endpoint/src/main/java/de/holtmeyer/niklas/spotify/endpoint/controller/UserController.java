package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.spotify.user.UserService;
import de.holtmeyer.niklas.spotify.endpoint.api.UserAPI;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController implements UserAPI<UserProfile> {
    @Autowired @Getter
    UserService userService;

    public Response<? extends UserProfile> get(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                     @PathVariable String user_id) {
        if(user_id.equals("current")) return getCurrent(accesstoken);
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.get(user_id);
    }
    public Response<? extends UserProfile> getCurrent(@CookieValue(name = "access_token") Optional<String> accesstoken) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.getProfile();
    }

    @GetMapping("/top/artists")
    public String getCurrentUsersProfile(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                         @RequestParam String limit, @RequestParam String offset) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.getTopArtists(limit, offset).toString();
    }

    @GetMapping("/following/artists")
    public String getCurrentUsersFollowedArtists(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                         @RequestParam String limit, @RequestParam String offset) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.getFollowedArtists(limit, offset).toString();
    }

    @GetMapping("/following/artists/{ids}")
    public String getCurrentUsersFollowedArtists(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                                 @PathVariable String ids) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.isFollowingArtist(ids).toString();
    }

    @GetMapping("/following/artists/")
    public String getCurrentUsersFollowedArtists(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                                 @RequestBody List<String> ids) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.isFollowingArtist(ids).toString();
    }

    @GetMapping("/following/users/{ids}")
    public String getCurrentUsersFollowedUsers(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                                 @PathVariable String ids) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.isFollowingUser(ids).toString();
    }

    @GetMapping("/following/users/")
    public String getCurrentUsersFollowedUsers(@CookieValue(name = "access_token") Optional<String> accesstoken,
                                                 @RequestBody List<String> ids) {
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.current.isFollowingUser(ids).toString();
    }

    @GetMapping("/test")
    public String test(@CookieValue(name = "access_token") Optional<String> accesstoken){
        userService.getAccessToken().setAccessToken(accesstoken.get());
        return userService.followPlaylist("3cEYpjA9oz9GiPac4AsH4n").toString();
    }
}
