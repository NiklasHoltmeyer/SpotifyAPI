package de.holtmeyer.niklas.spotify.data.entity.dto.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.Album;
import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import de.holtmeyer.niklas.spotify.data.entity.dto.ExternalIds;
import de.holtmeyer.niklas.spotify.data.entity.dto.Track;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PlaylistTrack extends HasHrefWithID {
    Album album;
    Artist[] artists;
    String[] available_markets;
    String disc_number;
    Long duration_ms;
    Boolean episode;
    Boolean explicit;
    ExternalIds external_ids;
    ExternalUrls external_urls;
    String is_local;
    String name;
    Long popularity;
    String preview_url;
    Object track;
    Integer track_number;
}
