package de.holtmeyer.niklas.spotify.data.service.spotify.api.track;

import de.holtmeyer.niklas.spotify.data.entity.dto.Track;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class TrackList {
    TrackAPI api;
    CurrentUserTrackService current;

    public <T> List<T> saved(Function<UserSavedTrack, T> mapper){
        return this.api.getCurrentSavedTracks()
                .getBody()
                .get()
                .stream()
                .map(mapper)
                .toList();
    }
}
