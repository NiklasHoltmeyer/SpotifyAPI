package de.holtmeyer.niklas.spotify.data.entity.dto.pagable;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserSavedTracks extends Pageable<UserSavedTrack>{}
