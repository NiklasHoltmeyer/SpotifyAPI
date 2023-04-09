package de.holtmeyer.niklas.spotify.data.service.spotify.api.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
    @Autowired public TrackAPI api;
    @Autowired public CurrentUserTrackService current;

}
