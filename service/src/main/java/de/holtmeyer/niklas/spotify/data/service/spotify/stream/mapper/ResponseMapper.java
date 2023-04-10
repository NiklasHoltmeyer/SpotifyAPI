package de.holtmeyer.niklas.spotify.data.service.spotify.stream.mapper;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;

import java.util.*;
import java.util.stream.Stream;

public class ResponseMapper {
   public static <T> Stream<T> asFlatStream(Response<List<T>> responses){
        var items = responeBodyList(responses);

        if(items == null){
            return Stream.empty();
        }

        return items.stream().flatMap(Collection::stream);
    }

    public static <T> List<T> asList(Response<List<T>> responses){
        return asFlatStream(responses).toList();
    }

    public static <T> T getBody(Response<T> reponse){
        return Optional.ofNullable(reponse).flatMap(Response::getBody).orElse(null);
    }

    public static <T> List<List<T>> responeBodyList(Response<List<T>> responses) {
       return Optional.ofNullable(responses).flatMap(Response::getBody).map(Arrays::asList).orElse(null);
    }
}
