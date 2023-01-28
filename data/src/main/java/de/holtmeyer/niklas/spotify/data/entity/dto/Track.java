package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Track {
    Album album;
    Artist[] artists;
    String[] available_markets;
    Integer disc_number;
    Integer duration_ms;
    Boolean explicit;
    ExternalIds external_ids;
    ExternalUrls external_urls;
    String href;
    String id;
    Boolean is_playable;
    LinkedFrom linked_from;
    Restrictions restrictions;
    String name;
    Integer popularity;
    String preview_url;
    Integer track_number;
    String type;
    String uri;
    Boolean is_local;
}
