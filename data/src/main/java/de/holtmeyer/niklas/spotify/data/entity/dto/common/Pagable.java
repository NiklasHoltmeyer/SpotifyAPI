package de.holtmeyer.niklas.spotify.data.entity.dto.common;

import lombok.Data;

@Data
public abstract class Pagable {
    String href;
    Integer limit;
    String next;
    Integer offset;
    String previous;
    Integer total;
}
