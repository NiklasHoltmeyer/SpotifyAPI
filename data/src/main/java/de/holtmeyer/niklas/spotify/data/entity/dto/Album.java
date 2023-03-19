package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Image;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Reason;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Album extends HasHrefWithID {
    String album_type;
    Integer total_tracks;
    String[] available_markets;
    ExternalUrls external_urls;
    Image[] images;
    String name;
    String release_date;
    String release_date_precision;
    Reason restrictions;
    String album_group;
    Artist[] artists;
}
