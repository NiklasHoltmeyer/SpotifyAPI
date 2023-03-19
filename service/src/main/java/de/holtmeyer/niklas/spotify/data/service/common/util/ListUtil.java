package de.holtmeyer.niklas.spotify.data.service.common.util;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ListUtil<T> {
    public static <T> Stream<List<T>> batches(List<T> source, int length) {
        //https://stackoverflow.com/a/30072617
        if (length <= 0){
            throw new IllegalArgumentException("length = " + length);
        }

        int size = source.size();

        if (size == 0){
            return Stream.empty();
        }

        int fullChunks = (size - 1) / length;

        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }
}
