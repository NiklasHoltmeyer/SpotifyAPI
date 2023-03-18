package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Audiobook {
    Author[] authors;
    String[] available_markets;
    Copyright[] copyrights;
    String description;
    String html_description;
    String edition;
    Boolean explicit;
    ExternalUrls external_urls;
    String href;
    String id;
    Image[] images;
    String[] languages;
    String media_type;
    String name;
    String publisher;
    String type;
    String uri;
    Integer total_chapters;
    Chapter chapters;
}
