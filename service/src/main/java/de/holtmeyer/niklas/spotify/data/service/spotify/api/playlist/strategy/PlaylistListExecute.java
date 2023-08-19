package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.strategy;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.PlaylistService;
import lombok.AllArgsConstructor;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
class PlaylistListExecute {
    PlaylistListStrategy listStrategy;
    PlaylistListFilterStrategy listFilterStrategy;
    PlaylistService playlistService;

    /*  userPlaylistsCurrentUserFollows
savedSongs
currentUserPlaylists */

    void execute(){
        var x1 = listStrategy.userPlaylistsCurrentUserFollows();
        var x2 = listStrategy.savedSongs();
        var x3 = listStrategy.currentUserPlaylists();
    }

    void x (){
        var playlistFilter = this.playlistFilter();
        var playlistMapper = this.playlistMapper();

        var userIDsOfPlaylistsCurrentUserFollows = this.playlistService.list.follows(playlistFilter, playlistMapper);

        var WUMBOPlayListIDs = this.playlistService.list.playlistUsersFollows(userIDsOfPlaylistsCurrentUserFollows);

//        var wombooo = this.currentAndPlaylistSongs();
//
//        var allSongUris = this.playlistService.list.list(WUMBOPlayListIDs, trackFilter.apply(wombooo))
//                .distinct()
//                .collect(Collectors.toList());
    }

    Predicate<PlaylistsWithMinimalTrackInfo> playlistFilter(){
        return null;
    }

    Function<PlaylistsWithMinimalTrackInfo, String> playlistMapper(){
        return null;
    }
}
