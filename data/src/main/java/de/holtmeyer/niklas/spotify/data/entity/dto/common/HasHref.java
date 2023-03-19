package de.holtmeyer.niklas.spotify.data.entity.dto.common;

import lombok.Data;

@Data
public abstract class HasHref {
    String href;
    String uri;
    String type;

    public String uriAsJson (){
        return String.format("{'uri': '%s'}", this.uri);
    }
}
