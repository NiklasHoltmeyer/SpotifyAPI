package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class IndexController {
    AccessToken accessToken;

    @SneakyThrows
    @GetMapping
    public void get(HttpServletResponse res) {
        var redirectUri = "http://localhost:4200/"; //TODO
        if(accessToken == null){
            redirectUri = "http://localhost:5555/spotify/auth/"; //TODO
        }

        res.sendRedirect(redirectUri);
    }
}
