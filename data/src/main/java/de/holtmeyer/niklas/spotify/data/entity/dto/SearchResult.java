package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import lombok.Data;

@Data
public class SearchResult {
    Pageable<Track> tracks;
    Pageable<Artist> artists;
    Pageable<Album> albums;
    Pageable<Playlist> playlists;
    Pageable<Show> shows;
    Pageable<Episode> episodes;
    Pageable<Audiobook> audiobooks;
}
