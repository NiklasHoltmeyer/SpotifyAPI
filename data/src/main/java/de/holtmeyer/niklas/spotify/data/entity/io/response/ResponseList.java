package de.holtmeyer.niklas.spotify.data.entity.io.response;

import kong.unirest.PagedList;
import lombok.Data;

import java.util.List;

@Data
public class ResponseList<T> {
    List<T> body;
    PagedList<T> httpResponse;

    public ResponseList(PagedList<T> httpPageResponse) {
        this.body = httpPageResponse.getBodies();
        this.httpResponse = httpPageResponse;
    }
}
