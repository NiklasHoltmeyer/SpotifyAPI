package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Albums {
    String href;
    Album[] items;
    Integer limit;
    String next;
    Integer offset;
    String previous;
    Integer total;
    String type;
    String uri;
}
