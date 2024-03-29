package de.holtmeyer.niklas.spotify.endpoint.controller;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import de.holtmeyer.niklas.spotify.data.entity.io.request.PlaylistDetailsRequestBody;
import de.holtmeyer.niklas.spotify.data.service.common.util.ListStream;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.artist.ArtistAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.PlaylistAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist.PlaylistService;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.search.SearchService;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.track.TrackAPI;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.track.TrackService;
import de.holtmeyer.niklas.spotify.data.service.spotify.api.user.UserService;
import de.holtmeyer.niklas.spotify.data.service.spotify.stream.filter.*;
import de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper.PlaylistMapper;
import de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper.ResponseMapper;
import de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper.TrackMapper;
import de.holtmeyer.niklas.spotify.endpoint.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
public class DevController {
    SearchService searchService;
    PlaylistAPI playlistAPI;
    PlaylistService playlistService;
    TrackAPI trackAPI;
    TrackService trackService;
    ArtistAPI artistAPI;
    UserService userService;

    final String USER_ID = "kngholdy";

    final Predicate<PlaylistsWithMinimalTrackInfo> filterGeneratedPlaylist = PlaylistFilter.descriptionNotContains("Generate-Date:");
    final List<String> trio = List.of("kngholdy", "Kutay Gündogan", "Ali Emngl");;

    @GetMapping("/dev/loworbitioncannon")
    public Object loworbitioncannon(){
        boolean shuffle = false;
        List<String> ignorePlaylistOwners = List.of("Spotify");
        Predicate<PlaylistsWithMinimalTrackInfo> playlistFilter = OwnerFilter.notInList(ignorePlaylistOwners).and(filterGeneratedPlaylist);
        Predicate<PlaylistTrack> nonNull = Objects::nonNull;
        Function<List<String>, Predicate<PlaylistTrack>> trackFilter = (trackBlacklist) -> nonNull
                .and(UriFilter.notInList(trackBlacklist));
        String dst = "Low Orbit Ion Cannon";
        String playlist_dst_id = findPlayListByName(dst);
        Function<PlaylistsWithMinimalTrackInfo, String> playlistMapper = PlaylistMapper::getOwnerID;

        this.playlistService.deleteAllTracks(playlist_dst_id);

        var playlistsCurrentUserFollows = this.playlistService.list.follows();

        if(playlistsCurrentUserFollows == null){
            return Optional.empty();
        }

        var userIDsOfPlaylistsCurrentUserFollows = this.playlistService.list.follows(playlistFilter, playlistMapper);
        var WUMBOPlayListIDs = this.playlistService.list.playlistUsersFollows(userIDsOfPlaylistsCurrentUserFollows);

        var wombooo = this.currentAndPlaylistSongs();

        var allSongUris = this.playlistService.list.list(WUMBOPlayListIDs, trackFilter.apply(wombooo))
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

        var desc = logDesc(allSongUris.size());

        this.playlistAPI.setDetails(playlist_dst_id, desc);

        return desc;
    }

    @GetMapping("/dev/wombo2combo")
    public Object wombocombo2(){
        boolean shuffle = false;
        String dst = "Low Orbit Ion Cannon - Minus";
        String playlist_dst_id = findPlayListByName(dst);
        List<String> ignorePlaylistOwners = List.of("Spotify");
        Predicate<PlaylistsWithMinimalTrackInfo> playlistFilter = OwnerFilter.notInList(ignorePlaylistOwners).and(filterGeneratedPlaylist);

        //Generated
        Function<PlaylistsWithMinimalTrackInfo, String> playlistMapper = PlaylistMapper::getOwnerID;

        Predicate<PlaylistTrack> nonNull = Objects::nonNull;
        Function<List<String>, Predicate<PlaylistTrack>> trackFilter = (trackBlacklist) -> nonNull
                .and(UriFilter.notInList(trackBlacklist))
                .and(filterByAge(14));

        this.playlistService.deleteAllTracks(playlist_dst_id);

        var userIDsOfPlaylistsCurrentUserFollows = this.playlistService.list.follows(playlistFilter, playlistMapper);

        var WUMBOPlayListIDs = this.playlistService.list.playlistUsersFollows(userIDsOfPlaylistsCurrentUserFollows);

        var wombooo = this.currentAndPlaylistSongs();

        var allSongUris = this.playlistService.list.list(WUMBOPlayListIDs, trackFilter.apply(wombooo))
                .distinct()
                .collect(Collectors.toList());

        this.sortByArtistPopularity(allSongUris);
        allSongUris.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());

        this.playlistAPI.addTracks(playlist_dst_id, allSongUris);

        var desc = logDesc(allSongUris.size());
        this.playlistAPI.setDetails(playlist_dst_id, desc);

        return desc;
    }
    @GetMapping("/dev/shuffle")
    public String shuffle(){
        String src = "ParkSink";
        String dst = "Rollercoaster Music";

        return shuffle(src, dst);
    }

    @GetMapping("/dev/politik")
    public String politik(){
        var dst = "non-biased Release Radar";
        var srcs = List.of("Discover Weekly", "Release Radar", "Deutschrap Brandneu", "Rap wieder echt");

        var tracksSortedByPopularity = this.sortByPopularity(srcs);
//        this.sortByAge(tracksSortedByPopularity);


        return overridePlaylist(dst, tracksSortedByPopularity);
    }

    @GetMapping("/dev/all")
    public String t(){
        loworbitioncannon();
        wombocombo2();
        politik();
        return "all";
    }

    public String shuffle(String playlist_src, String playlist_dst){
        String playlist_src_id = findPlayListByName(playlist_src);
        String playlist_dst_id = findPlayListByName(playlist_dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);
        var count = this.playlistService.copyAllTracks(playlist_src_id, playlist_dst_id, true);

        var desc = logDesc(count);
        this.playlistAPI.setDetails(playlist_dst_id, desc);
        return desc.getDescription();
    }

    public List<PlaylistTrack> sortByPopularity(List<String> playlist_srcs){
        var trackURIs = playlist_srcs.stream()
                .map(this::findPlayListByName)
                .map(this.playlistService.list::tracks)
                .flatMap(Collection::stream)
                .distinct()
                .filter(Objects::nonNull)
                .filter(filterByAge(6))
                .collect(Collectors.toList());

        // tracks.get(0).getAlbum().getRelease_date()

        // trackURIs -> getArtists [] -> id
        // comp erweitern um nach artists suchen?
        // ergebnise per hashmap cashen? :D
        sortByArtistPopularity(trackURIs);
        return trackURIs;
//        trackURIs.sort(Comparator.comparing(PlaylistTrack::getPopularity).reversed());

        //TODO Desc hinzufügen, damit klar ist welche map/filter angewendet
        //TODO Desc gen XX.XX.XXX hinzufügen - mit Infos
        // Infos Verbose und ohne mit Verbose wirklich sagen was rein und was raus gefiltert
        // -> filter: ignoiere Playlisten mit gen ... in disc
        //TODO Interface für Filter (mit Prio) und Filter ... Map ... // alles Chainable
        //Prio ->Popularity gem. anhand wie oft kommt der in meinen lieblingssongs v or
    }

    /**
     * Drop-Create Playlist
     */
    private String overridePlaylist(String playlist_dst, List<PlaylistTrack> trackURIs) {
        var playlist_dst_id = findPlayListByName(playlist_dst);

        this.playlistService.deleteAllTracks(playlist_dst_id);
        this.playlistService.api.addTracks(playlist_dst_id, trackURIs);

        var desc = logDesc(trackURIs.size());
        this.playlistAPI.setDetails(playlist_dst_id, desc);
        return desc.getDescription();
    }

    private static Predicate<PlaylistTrack> filterByAge(int maxAgeInDays) {
        return DateFilter.age(maxAgeInDays, TrackMapper::getReleaseDate, TrackMapper::getReleaseDatePrecision);
    }

    public void sortByArtistPopularity(List<PlaylistTrack> tracks){
        var artistsIDs = tracks.stream()
                .map(PlaylistTrack::getArtists)
                .map(List::of)
                .flatMap(Collection::stream)
                .distinct()
                .map(HasHrefWithID::getId)
                .toList();

        var artistPopularity = ResponseMapper.getBody(artistAPI.get(artistsIDs)).orElseThrow()
                .stream()
                .filter(ar -> Objects.nonNull(ar.getPopularity()))
                .collect(Collectors.toMap(HasHrefWithID::getId, Artist::getPopularity));

        Function<PlaylistTrack, Double> calcPopularity = (track) -> Stream.of(track.getArtists())
                    .map(HasHrefWithID::getId)
                    .mapToDouble(artistPopularity::get)
                    .average()
                    .orElseThrow();

        tracks.sort(Comparator.comparing(calcPopularity).reversed());
    }

    public List<PlaylistTrack> sortByAge(List<PlaylistTrack> tracks){
        tracks.sort(Comparator.comparing(TrackMapper::getReleaseDate).reversed());
        return tracks;
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
    List<String> currentAndPlaylistSongs(){ //filterGeneratedPlaylist
        var savedTracks = this.trackService.list.saved(TrackMapper::getUri);

        var ownerFilter = OwnerFilter.ownerID(USER_ID);

        var playlistFilter = filterGeneratedPlaylist.and(ownerFilter);

        var playlistTracks = this.playlistService.list.tracksUserFollows(playlistFilter);

        return ListStream.flatten(savedTracks, playlistTracks).distinct().toList();
    }

    private PlaylistDetailsRequestBody logDesc(int tracks){
        var date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        var desc = "Tracks: %s. Generate-Date: %s".formatted(tracks, date);

        return new PlaylistDetailsRequestBody(null, null, null, desc);
    }
}
