package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Artist {
    ExternalUrls external_urls;
    Followers followers;
    String[] genres;
    String href;
    String id;
    Image[] images;
    String name;
    Integer popularity;
    String type;
    String uri;
}
