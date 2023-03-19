package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Artist extends HasHrefWithID {
    ExternalUrls external_urls;
    Followers followers;
    String[] genres;
    Image[] images;
    String name;
    Integer popularity;
}
