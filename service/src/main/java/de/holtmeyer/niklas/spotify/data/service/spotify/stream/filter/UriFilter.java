package de.holtmeyer.niklas.spotify.data.service.spotify.stream.filter;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriFilter {
    public static Predicate<HasHref> inList(List<String> items){
        return ListFilter.inList(items, HasHref::getUri);
    }

    public static Predicate<HasHref> notInList(List<String> items){
        return Predicate.not(UriFilter.inList(items));
    }
}
