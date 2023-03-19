package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Reason;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Track extends HasHrefWithID {
    Album album;
    Artist[] artists;
    String[] available_markets;
    Integer disc_number;
    Integer duration_ms;
    Boolean explicit;
    ExternalIds external_ids;
    ExternalUrls external_urls;
    Boolean is_playable;
    LinkedFrom linked_from;
    Reason restrictions;
    String name;
    Integer popularity;
    String preview_url;
    Integer track_number;
    Boolean is_local;
}
