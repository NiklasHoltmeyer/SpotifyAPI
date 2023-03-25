package de.holtmeyer.niklas.spotify.data.entity.io.response;

import kong.unirest.PagedList;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class ResponseList<T> {
    List<? extends T> body;
    PagedList<T> httpResponse;

    public ResponseList(PagedList<T> httpPageResponse) {
        this.body = httpPageResponse.getBodies();
        this.httpResponse = httpPageResponse;
    }
}
