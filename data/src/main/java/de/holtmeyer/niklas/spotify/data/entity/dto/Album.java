package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Album {
    String album_type;
    Integer total_tracks;
    String[] available_markets;
    ExternalUrls external_urls;
    String href;
    String id;
    Image[] images;
    String name;
    String release_date;
    String release_date_precision;
    Restrictions restrictions;
    String type;
    String uri;
    String album_group;
    Artists artists;
}
