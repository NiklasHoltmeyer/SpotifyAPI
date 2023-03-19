package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Show extends HasHrefWithID {
    String[] available_markets;
    Copyright[] copyrights;
    String description;
    String html_description;
    Boolean explicit;
    ExternalUrls external_urls;
    Image[] images;
    Boolean is_externally_hosted;
    String[] languages;
    String media_type;
    String name;
    String publisher;
    String total_episodes;
}
