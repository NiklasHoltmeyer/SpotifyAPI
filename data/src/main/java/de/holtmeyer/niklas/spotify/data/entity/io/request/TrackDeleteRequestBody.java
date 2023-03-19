package de.holtmeyer.niklas.spotify.data.entity.io.request;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class TrackDeleteRequestBody {
    List<URI> tracks;
}
