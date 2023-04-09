package de.holtmeyer.niklas.spotify.data.service.spotify.filter;

import de.holtmeyer.niklas.spotify.data.entity.dto.Owner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListFilter {
    public static <T> Predicate<T> inList(List<String> items, Function<T, String> getKey){
        return owner -> items.contains(getKey.apply(owner));
    }

    public static <T> Predicate<T> notInList(List<String> items, Function<T, String> getKey){
        return Predicate.not(ListFilter.inList(items, getKey));
    }
}
