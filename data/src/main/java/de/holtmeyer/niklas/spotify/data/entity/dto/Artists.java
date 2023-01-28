package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Artists {
    String href;
    Artist[] items;
    Integer limit;
    String next;
    Cursors cursors;
    Integer total;
}
