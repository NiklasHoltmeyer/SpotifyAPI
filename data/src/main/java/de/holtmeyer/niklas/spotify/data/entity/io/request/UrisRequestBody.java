package de.holtmeyer.niklas.spotify.data.entity.io.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class UrisRequestBody {
    List<String> uris;

    public Map<String, Object> toQueryParameter(String key, String delimiter){
        return Map.of(key, String.join(delimiter, this.getUris()));
    }

    public Map<String, Object> toQueryParameter(String key){
        return this.toQueryParameter(key, ",");
    }
}
