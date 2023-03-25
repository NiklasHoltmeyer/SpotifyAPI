package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.service.spotify.artist.ArtistAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.playlist.PlaylistAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.playlist.PlaylistService;
import de.holtmeyer.niklas.spotify.data.service.spotify.search.SearchService;
import de.holtmeyer.niklas.spotify.data.service.spotify.track.TrackAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.track.TrackService;
import de.holtmeyer.niklas.spotify.endpoint.exception.EntityNotFoundException;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DevController {
    @Autowired
    SearchService searchService;
    @Autowired
    PlaylistAPI playlistAPI;

    @Autowired
    PlaylistService playlistService;

    @Autowired
    TrackAPI trackAPI;

    @Autowired
    TrackService trackService;

    @Autowired
    ArtistAPI artistAPI;

    @GetMapping("/dev/wombocombo")
    public Object wombocombo(){
        boolean shuffle = false;
        List<String> trio = List.of("kngholdy", "Kutay Gündogan", "Ali Emngl");
        List<String> ohneSpoti = List.of("Spotify");
        String dst = "Low Orbit Ion Cannon";
        String playlist_dst_id = findPlayListByName(dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);

        var playlistsCurrentUserFollows = this.playlistAPI
                .getCurrentUserPlaylists()
                .getBody()
                .get();

        var userIDsOfPlaylistsCurrentUserFollows = playlistsCurrentUserFollows
                .stream()
                .map(BasePlaylist::getOwner)
                .filter(x->!ohneSpoti.contains(x.getDisplay_name()))
                .map(HasHrefWithID::getId)
                .distinct()
                .toList();

        var WUMBOPlayListIDs = userIDsOfPlaylistsCurrentUserFollows.stream()
                .map(user_id -> this.playlistAPI.getUserPlaylists(user_id))
                .map(response -> response.getBody().get())
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .map(HasHrefWithID::getId)
                .distinct()
                .toList();

        var wombooo = this.currentAndPlaylistSongs();

        var allSongUris = WUMBOPlayListIDs.parallelStream()
                .map(this.playlistService::listTracks)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(x->!wombooo.contains(x.getUri()))
                .distinct()
                .collect(Collectors.toList());

        if(shuffle){
            Collections.shuffle(allSongUris);
            //((PlaylistTrack) allSongUris.get(0)).getPopularity()
        }else{
            // generell filter überlgen
            allSongUris.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());
        }

        this.playlistAPI.addTracks(playlist_dst_id, allSongUris);

        return "WUMBO";
    }
    @GetMapping("/dev/shuffle")
    public String shuffle(){
        String src = "ParkSink";
        String dst = "Rollercoaster Music";

        shuffle(src, dst);

        return "Ok";
    }

    @GetMapping("/dev/politik")
    public String politik(){
        var dst = "non-biased Release Radar";
        var srcs = List.of("Discover Weekly", "Release Radar", "Deutschrap Brandneu", "Rap wieder echt");

        this.sortByPopularity(srcs, dst);
        return "wupdi";
    }

    public void shuffle(String playlist_src, String playlist_dst){
        String playlist_src_id = findPlayListByName(playlist_src);
        String playlist_dst_id = findPlayListByName(playlist_dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);
        this.playlistService.copyAllTracks(playlist_src_id, playlist_dst_id, true);
    }

    public void sortByPopularity(List<String> playlist_srcs, String playlist_dst){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var now = LocalDate.parse(LocalDate.now().toString(), formatter);

        var trackURIs = playlist_srcs.stream()
                .map(this::findPlayListByName)
                .map(this.playlistService::listTracks)
                .flatMap(Collection::stream)
                .distinct()
                .filter(Objects::nonNull)
                .filter(
                        track -> {
                            var releaseDate = track.getAlbum().getRelease_date();
                            LocalDate releaseDateTime = LocalDate.parse(releaseDate, formatter);
                            var ageInDays = now.toEpochDay() - releaseDateTime.toEpochDay();
                            return ageInDays < 14;
                        }
                )
                .collect(Collectors.toList());

        // tracks.get(0).getAlbum().getRelease_date()

        // trackURIs -> getArtists [] -> id
        // comp erweitern um nach artists suchen?
        // ergebnise per hashmap cashen? :D
        sortByArtistPopularity(trackURIs);
//        trackURIs.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());

        var playlist_dst_id = findPlayListByName(playlist_dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);
        this.playlistService.api.addTracks(playlist_dst_id, trackURIs);
    }

    public void sortByArtistPopularity(List<PlaylistTrack> tracks){
        var artistsIDs = tracks.stream()
                .map(PlaylistTrack::getArtists)
                .map(List::of)
                .flatMap(Collection::stream)
                .distinct()
                .map(HasHrefWithID::getId)
                .toList();

        var artistPopularity = artistAPI.get(artistsIDs)
                .getBody().get()
                .stream()
                .collect(Collectors.toMap(Artist::getName, Artist::getPopularity));

        Function<PlaylistTrack, Double> calcPopularity = (track) -> Stream.of(track.getArtists())
                    .map(Artist::getName)
                    .mapToDouble(artistPopularity::get)
                    .average()
                    .orElseThrow(() -> new RuntimeException("todo"));

        tracks.sort(Comparator.comparing(calcPopularity).reversed());
    }

    private String findPlayListByName(String playlistname) {
        return playlistService.current.findByName(playlistname).map(HasHrefWithID::getId).orElseThrow(playlistNotFound(playlistname));
    }

    Supplier<EntityNotFoundException> playlistNotFound(String playlistname){
        return () -> new EntityNotFoundException(String.format("Could not find Playlist %s", playlistname));
    }

    public static void printTimeStamp(Collection c) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp + " " + c.size());
    }
    List<String> currentAndPlaylistSongs(){
        var savedTracks = this.trackService.current.listSaved().stream()
                .map(HasHref::getUri)
                .toList();
// 16
        var excludePlaylists = List.of("Low Orbit Ion Cannon", "WUMBO", "Low Orbit Ion Cannon - Minus");
        var playlistTracks = this.playlistAPI
                .getCurrentUserPlaylists()
                .getBody()
                .get()
                .stream()
                .filter(x->x.getOwner().getId().equals("kngholdy"))
                .filter(x->!excludePlaylists.contains(x.getName()))
                .map(HasHrefWithID::getId)
                .distinct()
                .map(this.playlistService::listTracks)
                .flatMap(Collection::stream)
                .distinct()
                .map(HasHref::getUri)
                .toList();

        return Stream.of(savedTracks, playlistTracks)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }
}
