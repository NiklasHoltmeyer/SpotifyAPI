package de.holtmeyer.niklas.spotify.data.entity.io.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TrackReorderRequestBody {
    String snapshot_id;
    int range_start;
    int insert_before;
    int range_length;
}
