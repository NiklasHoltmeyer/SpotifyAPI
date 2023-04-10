package de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;

import java.util.*;
import java.util.stream.Stream;

public class ResponseMapper {
   public static <T> Stream<T> asFlatStream(Response<List<T>> responses){
        var items = responses.getBody().map(Arrays::asList).orElse(null);

        if(items == null){
            return Stream.empty();
        }

        return items.stream().flatMap(Collection::stream);
    }

    public static <T> List<T> asList(Response<List<T>> responses){
        return asFlatStream(responses).toList();
    }
}
