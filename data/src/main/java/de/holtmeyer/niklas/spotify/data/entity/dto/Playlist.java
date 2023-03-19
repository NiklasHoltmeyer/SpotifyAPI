package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Playlist extends BasePlaylist<Track[]> {}
