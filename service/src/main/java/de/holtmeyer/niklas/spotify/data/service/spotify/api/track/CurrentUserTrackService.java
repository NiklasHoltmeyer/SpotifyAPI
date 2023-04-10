package de.holtmeyer.niklas.spotify.data.service.spotify.api.track;

import de.holtmeyer.niklas.spotify.data.entity.dto.Track;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class CurrentUserTrackService {
    TrackAPI trackAPI;
}
