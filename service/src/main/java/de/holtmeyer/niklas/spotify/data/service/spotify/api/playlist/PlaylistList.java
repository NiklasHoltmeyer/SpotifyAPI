package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PlaylistList {
    @Autowired public PlaylistAPI api;
    @Autowired public CurrentUserPlaylistService current;

    /**
     * Returns all tracks of a playlist. Throws an exception if no valid response is returned. Returns a mutable list.
     */
    public List<PlaylistTrack> tracks(String playlist_id) {
        return this.api.getTracks(playlist_id, null, null, null)
                .getBody()
                .orElseThrow(() -> new RuntimeException(String.format("Could not Find Playlist: %s", playlist_id)))
                .stream()
                .map(x->x.getTrack())// Methode Reference does not work sadly
                .collect(Collectors.toList());
    }

    /**
     * List all Playlists the Current User Follows. Throws an exception if no valid response is returned.
     */
    public List<PlaylistsWithMinimalTrackInfo> follows(){
        return this.current.follows()
                .getBody()
                .orElseThrow();
    }

    /**
     * List all tracks of all playlists the current user is following.
     */
    public List<String> tracksUserFollows(Predicate<PlaylistsWithMinimalTrackInfo> playlistsFilter, Predicate<PlaylistTrack> trackFilter, Boolean distinct){
        var playlistTracksStream = this.follows()
                .parallelStream()
                .filter(playlistsFilter)
                .map(HasHrefWithID::getId)
                .distinct()
                .map(this::tracks)
                .flatMap(Collection::stream)
                .filter(trackFilter);

        if(Optional.ofNullable(distinct).orElse(false)){
            playlistTracksStream = playlistTracksStream.distinct();
        }

        return playlistTracksStream
                .map(HasHref::getUri)
                .toList();
    }

    /**
     * List all tracks of all playlists the current user is following.
     */
    public List<String> tracksUserFollows(Predicate<PlaylistsWithMinimalTrackInfo> playlistsFilter, Predicate<PlaylistTrack> trackFilter){
        return this.tracksUserFollows(playlistsFilter, trackFilter, true);
    }
}
