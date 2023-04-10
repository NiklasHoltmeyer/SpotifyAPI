package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
        return tracks(playlist_id, 0);
    }

    @SneakyThrows
    private List<PlaylistTrack> tracks(String playlist_id, int retry) {
        var response =  this.api.getTracks(playlist_id, null, null, null)
                .getBody();

        if(response.isPresent()){
            return response.get().stream().map(x->x.getTrack()).collect(Collectors.toList()); // Methode Reference does not work sadly
        }else if(retry > 2){
            throw new RuntimeException(String.format("Could not Find Playlist: %s. Retries: %d", playlist_id, retry));
        }

        TimeUnit.SECONDS.sleep(retry);
        return tracks(playlist_id, (retry + 1));
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
