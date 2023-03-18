package de.holtmeyer.niklas.spotify.data.service.configuration;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service @Data
public class ClientConfiguration {
    String ID;
    String SECRET;
    String SCOPE;
    String REDIRECT_URI;
}
