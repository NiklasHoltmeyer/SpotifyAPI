package de.holtmeyer.niklas.spotify.data.entity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class PlaylistDetails {
    String name;
    @SerializedName("public") @Accessors(fluent = true)
    Boolean isPublic;
    Boolean collaborative;
    String description;
}
