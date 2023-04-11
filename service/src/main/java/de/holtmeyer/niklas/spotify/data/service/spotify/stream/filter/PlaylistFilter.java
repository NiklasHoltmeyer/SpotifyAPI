package de.holtmeyer.niklas.spotify.data.service.spotify.stream.filter;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.BasePlaylist;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistFilter {

    public static Predicate<PlaylistsWithMinimalTrackInfo> inList(List<String> names){
        return ListFilter.inList(names, PlaylistFilter::getPlaylistName);
    }

    public static Predicate<PlaylistsWithMinimalTrackInfo> notInList(List<String> names){
        return Predicate.not(OwnerFilter.inList(names));
    }

    private static String getPlaylistName(PlaylistsWithMinimalTrackInfo playlistInfo){
        return Optional.ofNullable(playlistInfo)
                .map(BasePlaylist::getName)
                .orElse(null);
    }

    public static Predicate<PlaylistsWithMinimalTrackInfo> descriptionContains(String value){
        return playlist -> playlist.getDescription().contains(value);
    }

    public static Predicate<PlaylistsWithMinimalTrackInfo> descriptionNotContains(String value){
        return Predicate.not(PlaylistFilter.descriptionContains(value));
    }
}
