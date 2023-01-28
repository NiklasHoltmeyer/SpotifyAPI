package de.holtmeyer.niklas.spotify.data.service.authorization;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service @Data
public class AccessToken {
    String accessToken;
}
