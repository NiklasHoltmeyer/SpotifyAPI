package de.holtmeyer.niklas.spotify.data.entity.dto.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PlaylistTracksInfo extends HasHrefWithID {
    Integer total;
}