package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class UserProfile {
    String country;
    String display_name;
    String email;
    ExplicitContent explicit_content;
    ExternalUrls external_urls;
    Followers followers;
    String href;
    String id;
    Image[] images;
    String product;
    String type;
    String uri;
}
