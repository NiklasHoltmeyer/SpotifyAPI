package de.holtmeyer.niklas.spotify.data.service.spotify.stream.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListFilter {
    public static <T> Predicate<T> inList(List<String> items, Function<T, String> getKey){
        return list -> items.contains(getKey.apply(list));
    }

    public static <T> Predicate<T> notInList(List<String> items, Function<T, String> getKey){
        return Predicate.not(ListFilter.inList(items, getKey));
    }

    public static <T> boolean includeAll(T t){
        return true;
    }
}
