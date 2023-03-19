package de.holtmeyer.niklas.spotify.data.entity.dto.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.AddedBy;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Url;
import lombok.Data;

@Data
public class PlaylistTrackInfo {
    String added_at;
    AddedBy added_by;
    Object primary_color;
    Boolean is_local;
    PlaylistTrack track;
    Url video_thumbnail;
}
