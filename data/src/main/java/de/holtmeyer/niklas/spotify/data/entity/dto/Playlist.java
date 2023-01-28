package de.holtmeyer.niklas.spotify.data.entity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
public class Playlist {
    Boolean collaborative;
    String description;
    ExternalUrls external_urls;
    Followers followers;
    String href;
    String id;
    Image[] images;
    String name;
    Owner owner;
    @SerializedName("public") @Accessors(fluent = true)
    Boolean isPublic;
    String snapshot_id;
    Tracks tracks;
}
