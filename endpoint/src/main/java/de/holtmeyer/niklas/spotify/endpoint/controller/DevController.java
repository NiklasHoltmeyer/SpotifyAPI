package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrackInfo;
import de.holtmeyer.niklas.spotify.data.service.spotify.playlist.PlaylistService;
import de.holtmeyer.niklas.spotify.data.service.spotify.search.SearchService;
import de.holtmeyer.niklas.spotify.endpoint.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
public class DevController {
    @Autowired
    SearchService searchService;
    @Autowired
    PlaylistService playlistService;

    @GetMapping("/dev/wombocombo")
    public Object wombocombo(){
        boolean shuffle = true;
        List<String> trio = List.of("kngholdy", "Kutay Gündogan", "Ali Emngl");
        List<String> ohneSpoti = List.of("Spotify");
        String dst = "Low Orbit Ion Cannon";
        String playlist_dst_id = findPlayListByName(dst);

        deleteAllTracks(playlist_dst_id);

        var playlistsCurrentUserFollows = this.playlistService
                .getCurrentUserPlaylists()
                .getBody()
                .get();

        var userIDsOfPlaylistsCurrentUserFollows = playlistsCurrentUserFollows.stream()
                .map(BasePlaylist::getOwner)
                .filter(x->!ohneSpoti.contains(x.getDisplay_name()))
                .map(HasHrefWithID::getId)
                .distinct()
                .toList();

        var WUMBOPlayListIDs = userIDsOfPlaylistsCurrentUserFollows.stream()
                .map(user_id -> this.playlistService.getUserPlaylists(user_id))
                .map(response -> response.getBody().get())
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .map(HasHrefWithID::getId)
                .distinct()
                .toList();

        printTimeStamp(WUMBOPlayListIDs);

        var allSongUris = WUMBOPlayListIDs.stream().map(id -> listAllTrackURIs(id, false))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        printTimeStamp(allSongUris);

        if(shuffle){
            Collections.shuffle(allSongUris);
        }
        printTimeStamp(allSongUris);

        this.playlistService.addTracks(playlist_dst_id, allSongUris);

        return "WUMBO";
    }
    @GetMapping("/dev/shuffle")
    public String shuffle(){
        String src = "ParkSink";
        String dst = "Rollercoaster Music";

        shuffle(src, dst);

        return "Ok";
    }

    public void shuffle(String playlist_src, String playlist_dst){
        String playlist_src_id = findPlayListByName(playlist_src);
        String playlist_dst_id = findPlayListByName(playlist_dst);

        deleteAllTracks(playlist_dst_id);
        copyAllTracks(playlist_src_id, playlist_dst_id, true);
    }

    public void deleteAllTracks(String playlist_id){
        var tracks = playlistService.getTracks(playlist_id, null, null, null)
                .getBody().orElse(null);

        if(tracks == null){
            return;
        }

        var trackuris = tracks.stream()
                .map(PlaylistTrackInfo::getTrack)
                .map(HasHref.class::cast)
                .toList();

        playlistService.removeTracks(playlist_id, trackuris);
    }

    public void copyAllTracks(String playlist_src_id, String playlist_dst_id, boolean shuffle){
        var trackURIs = listAllTrackURIs(playlist_src_id, shuffle);

        this.playlistService.addTracks(playlist_dst_id, trackURIs);
    }

    private List<HasHref> listAllTrackURIs(String playlist_id, boolean shuffle) {
        var tracks = this.playlistService
                .getTracks(playlist_id, null, null, null)
                .getBody().orElse(null);

        if(tracks==null){
            return Collections.emptyList();
        }

        var trackURIs = tracks.stream()
                .map(PlaylistTrackInfo::getTrack)
                .map(HasHref.class::cast)
                .collect(Collectors.toList());

        if(shuffle){
            Collections.shuffle(trackURIs);
        }

        return trackURIs;
    }

    private String findPlayListByName(String playlistname) {
        var playlist = this.playlistService.getCurrentUserPlaylists()
                .getBody()
                .get()
                .stream()
                .filter(x->x.getName().contains(playlistname))
                .findFirst()
                .orElseThrow(playlistNotFound(playlistname));

        return playlist.getId();
    }

    Supplier<EntityNotFoundException> playlistNotFound(String playlistname){
        return () -> new EntityNotFoundException(String.format("Could not find Playlist %s", playlistname));
    }

    public static void printTimeStamp(Collection c) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp + " " + c.size());
    }
}
