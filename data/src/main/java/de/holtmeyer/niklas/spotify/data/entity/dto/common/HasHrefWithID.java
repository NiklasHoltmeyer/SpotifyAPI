package de.holtmeyer.niklas.spotify.data.entity.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public abstract class HasHrefWithID extends HasHref{
    String id;
}
