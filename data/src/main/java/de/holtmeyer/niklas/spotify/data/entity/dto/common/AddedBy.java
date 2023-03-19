package de.holtmeyer.niklas.spotify.data.entity.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AddedBy extends HasHrefWithID {
    ExternalUrls external_urls;
}
