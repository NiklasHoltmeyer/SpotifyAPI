package de.holtmeyer.niklas.spotify.data.service.common.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ListStream {
    public static <T> Stream<T> flatten(List<T>... values){
        return Stream.of(values)
                .flatMap(Collection::stream);
    }
}
