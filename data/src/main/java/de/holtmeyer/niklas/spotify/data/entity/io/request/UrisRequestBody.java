package de.holtmeyer.niklas.spotify.data.entity.io.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UrisRequestBody {
    List<String> uris;
}
