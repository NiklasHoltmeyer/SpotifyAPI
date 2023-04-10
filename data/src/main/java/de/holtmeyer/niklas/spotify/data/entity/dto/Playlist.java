package de.holtmeyer.niklas.spotify.data.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Image;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import lombok.Data;
import lombok.EqualsAndHashCode;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=true)
public class Playlist extends BasePlaylist<de.holtmeyer.niklas.spotify.data.entity.dto.pagable.Tracks> {
}
