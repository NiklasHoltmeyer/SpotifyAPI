package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.entity.io.request.PlaylistDetailsRequestBody;
import de.holtmeyer.niklas.spotify.data.service.common.util.ListStream;
import de.holtmeyer.niklas.spotify.data.service.common.util.ListUtil;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.artist.ArtistAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.PlaylistAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.PlaylistService;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.search.SearchService;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.track.TrackAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.track.TrackService;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.user.UserService;
import de.holtmeyer.niklas.spotify.data.service.spotify.filter.ListFilter;
import de.holtmeyer.niklas.spotify.data.service.spotify.filter.OwnerFilter;
import de.holtmeyer.niklas.spotify.data.service.spotify.filter.UriFilter;
import de.holtmeyer.niklas.spotify.endpoint.api.UserAPI;
import de.holtmeyer.niklas.spotify.endpoint.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
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

    @Autowired
    UserService userService;

    @GetMapping("/dev/wombocombo")
    public Object wombocombo(){
        boolean shuffle = false;
        List<String> trio = List.of("kngholdy", "Kutay Gündogan", "Ali Emngl");
        List<String> ignorePlaylistOwners = List.of("Spotify");
        String dst = "Low Orbit Ion Cannon";
        String playlist_dst_id = findPlayListByName(dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);

        var playlistsCurrentUserFollows = this.playlistAPI
                .getCurrentUserPlaylists()
                .getBody()
                .orElse(null);

        if(playlistsCurrentUserFollows == null){
            return Optional.empty();
        }

        var userIDsOfPlaylistsCurrentUserFollows = playlistsCurrentUserFollows
                .stream()
                .filter(OwnerFilter.notInList(ignorePlaylistOwners))
                .map(BasePlaylist::getOwner)
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
                .filter(UriFilter.notInList(wombooo))
                .distinct()
                .collect(Collectors.toList());

        if(false){ //sort alg
            Collections.shuffle(allSongUris);
            //((PlaylistTrack) allSongUris.get(0)).getPopularity()
        }else{
            // generell filter überlgen
            allSongUris.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());
        }

//        this.sortByArtistPopularity(allSongUris);

        this.playlistAPI.addTracks(playlist_dst_id, allSongUris);

        var desc = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.playlistAPI.setDetails(playlist_dst_id, new PlaylistDetailsRequestBody(null, null, null, desc));

        return "WUMBO";
    }

    @GetMapping("/dev/wombo2combo")
    public Object wombocombo2(){
        boolean shuffle = false;
        List<String> trio = List.of("kngholdy", "Kutay Gündogan", "Ali Emngl");
        String dst = "Low Orbit Ion Cannon - Minus";
        String playlist_dst_id = findPlayListByName(dst);
        List<String> ignorePlaylistOwners = List.of("Spotify");

        this.playlistService.deleteAllTracks(playlist_dst_id);

        var playlistsCurrentUserFollows = this.playlistAPI
                .getCurrentUserPlaylists()
                .getBody()
                .get();

        var userIDsOfPlaylistsCurrentUserFollows = playlistsCurrentUserFollows
                .stream()
                .filter(OwnerFilter.notInList(ignorePlaylistOwners))
                .map(BasePlaylist::getOwner)
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
                .filter(filterByAge(14))
                .distinct()
                .collect(Collectors.toList());

        this.sortByArtistPopularity(allSongUris);
        allSongUris.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());

        this.playlistAPI.addTracks(playlist_dst_id, allSongUris);

        var desc = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.playlistAPI.setDetails(playlist_dst_id, new PlaylistDetailsRequestBody(null, null, null, desc));

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

    @GetMapping("/dev/all")
    public String t(){
        wombocombo();
        wombocombo2();
        politik();
        return "all";
    }

    public void shuffle(String playlist_src, String playlist_dst){
        String playlist_src_id = findPlayListByName(playlist_src);
        String playlist_dst_id = findPlayListByName(playlist_dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);
        this.playlistService.copyAllTracks(playlist_src_id, playlist_dst_id, true);

        var desc = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.playlistAPI.setDetails(playlist_dst_id, new PlaylistDetailsRequestBody(null, null, null, desc));
    }

    public void sortByPopularity(List<String> playlist_srcs, String playlist_dst){
        var trackURIs = playlist_srcs.stream()
                .map(this::findPlayListByName)
                .map(this.playlistService::listTracks)
                .flatMap(Collection::stream)
                .distinct()
                .filter(Objects::nonNull)
                .filter(filterByAge(14))
                .collect(Collectors.toList());

        // tracks.get(0).getAlbum().getRelease_date()

        // trackURIs -> getArtists [] -> id
        // comp erweitern um nach artists suchen?
        // ergebnise per hashmap cashen? :D
        sortByArtistPopularity(trackURIs);
//        trackURIs.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());

        //TODO Desc hinzufügen, damit klar ist welche map/filter angewendet
        //TODO Desc gen XX.XX.XXX hinzufügen - mit Infos
        // Infos Verbose und ohne mit Verbose wirklich sagen was rein und was raus gefiltert
        // -> filter: ignoiere Playlisten mit gen ... in disc
        //TODO Interface für Filter (mit Prio) und Filter ... Map ... // alles Chainable
        //Prio ->Popularity gem. anhand wie oft kommt der in meinen lieblingssongs v or

        var playlist_dst_id = findPlayListByName(playlist_dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);
        this.playlistService.api.addTracks(playlist_dst_id, trackURIs);

        var desc = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.playlistAPI.setDetails(playlist_dst_id, new PlaylistDetailsRequestBody(null, null, null, desc));
    }

    private static Predicate<PlaylistTrack> filterByAge(int maxAgeInDays) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var now = LocalDate.parse(LocalDate.now().toString(), formatter);

        return track -> {
            try{
                var releaseDate = track.getAlbum().getRelease_date();
                LocalDate releaseDateTime = LocalDate.parse(releaseDate, formatter);
                var ageInDays = now.toEpochDay() - releaseDateTime.toEpochDay();
                return ageInDays < maxAgeInDays;
            }catch(Exception e){
                // yyyy -> 2009
                // yyyy-mm -> 1974-11
//                System.out.println("DATE: " + track.getAlbum().getRelease_date());
                return false;
            }

        };
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
                .collect(Collectors.toMap(HasHrefWithID::getId, Artist::getPopularity));

        Function<PlaylistTrack, Double> calcPopularity = (track) -> Stream.of(track.getArtists())
                    .map(HasHrefWithID::getId)
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

        var excludePlaylists = List.of("Low Orbit Ion Cannon", "WUMBO", "Low Orbit Ion Cannon - Minus");
        var playlistTracks = this.playlistAPI
                .getCurrentUserPlaylists()
                .getBody()
                .get()
                .parallelStream()
                .filter(x->x.getOwner().getId().equals("kngholdy"))
                .filter(x->!excludePlaylists.contains(x.getName()))
                .map(HasHrefWithID::getId)
                .distinct()
                .map(this.playlistService::listTracks)
                .flatMap(Collection::stream)
                .distinct()
                .map(HasHref::getUri)
                .toList();

        return ListStream.flatten(savedTracks, playlistTracks).distinct().toList();
    }
}
