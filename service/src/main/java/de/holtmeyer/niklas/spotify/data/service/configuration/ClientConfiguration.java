package de.holtmeyer.niklas.spotify.data.service.configuration;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service @Data
public class ClientConfiguration {
    String ID;
    String SECRET;
    final String SCOPE = "playlist-modify-private user-follow-read user-library-read user-top-read user-read-recently-played playlist-modify-private playlist-read-collaborative playlist-read-private playlist-modify-public";
    final String REDIRECT_URI = "http://localhost:5555/spotify/auth/callback";
}
