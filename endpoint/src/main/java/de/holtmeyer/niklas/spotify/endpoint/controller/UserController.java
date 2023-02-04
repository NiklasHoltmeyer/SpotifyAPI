package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artists;
import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.UsersTopArtistsResponse;
import de.holtmeyer.niklas.spotify.data.service.spotify.user.UserService;
import de.holtmeyer.niklas.spotify.endpoint.api.UserAPI;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserAPI<UserProfile> {
    @Autowired @Getter
    UserService userService;

    public Response<? extends UserProfile> get(@PathVariable String user_id) {
        if(user_id.equals("current")) return getCurrent();
        return userService.get(user_id);
    }

    public Response<? extends UserProfile> getCurrent() {
        return userService.current.getProfile();
    }

    public Response<? extends UsersTopArtistsResponse> getCurrentUsersTopArtists(@RequestParam String limit, @RequestParam String offset) {
        return userService.current.getTopArtists(limit, offset);
    }

    public Response<? extends Artists> getCurrentUsersFollowedArtists(@RequestParam String limit, @RequestParam String offset) {
        return userService.current.getFollowedArtists(limit, offset);
    }

    public Response<? extends List<Boolean>> isCurrentUsersFollowedArtists(@PathVariable String ids) {
        return userService.current.isFollowingArtist(ids);
    }

    public Response<? extends List<Boolean>> isCurrentUsersFollowedArtists(@RequestBody List<String> ids) {
        return userService.current.isFollowingArtist(ids);
    }

    public Response<? extends List<Boolean>> isCurrentUsersFollowedUsers(@PathVariable String ids) {
        return userService.current.isFollowingUser(ids);
    }

    public Response<? extends List<Boolean>> isCurrentUsersFollowedUsers(@RequestBody List<String> ids) {
        return userService.current.isFollowingUser(ids);
    }
}
