package de.holtmeyer.niklas.spotify.data.service.spotify.api.track;

import de.holtmeyer.niklas.spotify.data.entity.dto.Track;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentUserTrackService {
    @Autowired TrackAPI trackAPI;

    public List<Track> listSaved(){
        return this.trackAPI.getCurrentSavedTracks()
                .getBody()
                .get()
                .stream()
                .map(UserSavedTrack::getTrack)
                .toList();
    }
}
