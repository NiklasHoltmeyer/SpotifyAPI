package de.holtmeyer.niklas.spotify.data.service.spotify.filter;

import de.holtmeyer.niklas.spotify.data.entity.dto.Owner;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OwnerFilter {
    public static Predicate<PlaylistsWithMinimalTrackInfo> inList(List<String> names){
        return ListFilter.inList(names, OwnerFilter::getDisplayName);
    }

    public static Predicate<PlaylistsWithMinimalTrackInfo> notInList(List<String> names){
        return Predicate.not(OwnerFilter.inList(names));
    }

    private static String getDisplayName(PlaylistsWithMinimalTrackInfo playlistInfo){
        return Optional.ofNullable(playlistInfo)
                .map(PlaylistsWithMinimalTrackInfo::getOwner)
                .map(Owner::getDisplay_name)
                .orElse(null);
    }

    private static String getDisplayName(Owner owner){
        return Optional.ofNullable(owner)
                .map(Owner::getDisplay_name)
                .orElse(null);
    }
}
