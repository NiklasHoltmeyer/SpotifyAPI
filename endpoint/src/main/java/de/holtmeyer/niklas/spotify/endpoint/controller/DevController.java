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
        List<String> trio = List.of("kngholdy", "Kutay Gündogan", "Ali Emngl");
        String dst = "WUMBO";

        var playlistsCurrentUserFollows = this.playlistService
                .getCurrentUserPlaylists()
                .getBody()
                .get();

        var userIDsOfPlaylistsCurrentUserFollows = playlistsCurrentUserFollows.stream()
                .map(BasePlaylist::getOwner)
                .filter(x->trio.contains(x.getDisplay_name()))
                .map(HasHrefWithID::getId)
                .distinct()
                .toList();

        var WUMBO = userIDsOfPlaylistsCurrentUserFollows.stream()
                .map(user_id -> this.playlistService.getUserPlaylists(user_id))
                .map(response -> response.getBody().get())
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .map(HasHrefWithID::getId)
                .distinct()
                .toList();
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
        var tracks = playlistService.getTracks(playlist_id, null, null, null);
        var trackuris = tracks.getBody().get().stream()
                .map(PlaylistTrackInfo::getTrack)
                .map(HasHref.class::cast)
                .toList();

        playlistService.removeTracks(playlist_id, trackuris);
    }

    public void copyAllTracks(String playlist_src_id, String playlist_dst_id, boolean shuffle){
        var tracks = playlistService.getTracks(playlist_src_id, null, null, null);
        var trackURIs = tracks.getBody().get().stream()
                .map(PlaylistTrackInfo::getTrack)
                .map(HasHref.class::cast)
                .collect(Collectors.toList());

        if(shuffle){
            Collections.shuffle(trackURIs);
        }

        playlistService.addTracks(playlist_dst_id, trackURIs);
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



}
