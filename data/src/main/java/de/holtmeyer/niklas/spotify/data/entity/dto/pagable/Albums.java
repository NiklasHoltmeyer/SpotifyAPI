package de.holtmeyer.niklas.spotify.data.entity.dto.pagable;

import de.holtmeyer.niklas.spotify.data.entity.dto.Album;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrackInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=true)
public class Albums extends Pageable<Album> {}
