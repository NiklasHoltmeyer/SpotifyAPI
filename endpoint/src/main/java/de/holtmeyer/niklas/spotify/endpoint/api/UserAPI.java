package de.holtmeyer.niklas.spotify.endpoint.api;

import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Validated
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserAPI<T> {
    @GetMapping("/{user_id}")
    Response<? extends T> get(@CookieValue(name = "access_token") Optional<String> accesstoken, @PathVariable String user_id);

    @GetMapping("/current")
    Response<? extends UserProfile> getCurrent(@CookieValue(name = "access_token") Optional<String> accesstoken);
}
