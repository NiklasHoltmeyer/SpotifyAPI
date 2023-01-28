package de.holtmeyer.niklas.spotify.data.entity.io.response;

import lombok.Data;

@Data
public class UsersTopItemsResponse<T> {
    String href;
    T[] items;
    Integer limit;
    String next;
    Integer offset;
    String previous;
    Integer total;
}
