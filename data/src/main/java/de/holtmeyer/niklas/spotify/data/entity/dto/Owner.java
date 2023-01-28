package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Owner {
    ExternalUrls external_urls;
    Followers followers;
    String href;
    String id;
    String type;
    String uri;
    String display_name;
}
