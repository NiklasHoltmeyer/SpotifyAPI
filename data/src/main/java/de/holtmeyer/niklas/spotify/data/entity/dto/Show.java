package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Show {
    String[] available_markets;
    Copyright[] copyrights;
    String description;
    String html_description;
    Boolean explicit;
    ExternalUrls external_urls;
    String href;
    String id;
    Image[] images;
    Boolean is_externally_hosted;
    String[] languages;
    String media_type;
    String name;
    String publisher;
    String type;
    String uri;
    String total_episodes;
}
