package de.holtmeyer.niklas.spotify.data.entity.dto.track;

import de.holtmeyer.niklas.spotify.data.entity.dto.Track;
import lombok.Data;

@Data
public class UserSavedTrack {
    String added_at;
    Track track;
}
