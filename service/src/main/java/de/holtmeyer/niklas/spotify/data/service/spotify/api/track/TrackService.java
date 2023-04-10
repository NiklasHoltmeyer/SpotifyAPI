package de.holtmeyer.niklas.spotify.data.service.spotify.api.track;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrackService {
    public TrackAPI api;
    public CurrentUserTrackService current;
    public TrackList list;
}
