package de.holtmeyer.niklas.spotify.data.entity.dto.pagable;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Tracks extends Pageable<Object> {}
