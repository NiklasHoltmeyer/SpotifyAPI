package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Owner extends HasHrefWithID {
    ExternalUrls external_urls;
    Followers followers;
    String display_name;
}
