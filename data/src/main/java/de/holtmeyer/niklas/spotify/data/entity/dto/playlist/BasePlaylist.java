package de.holtmeyer.niklas.spotify.data.entity.dto.playlist;

import com.google.gson.annotations.SerializedName;
import de.holtmeyer.niklas.spotify.data.entity.dto.Followers;
import de.holtmeyer.niklas.spotify.data.entity.dto.Owner;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.ExternalUrls;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHrefWithID;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=true)
public class BasePlaylist<TracksType> extends HasHrefWithID {
    Boolean collaborative;
    String description;
    ExternalUrls external_urls;
    Followers followers;
    Image[] images;
    String name;
    Owner owner;
    @SerializedName("public") @Accessors(fluent = true)
    Boolean isPublic;
    String snapshot_id;
    TracksType tracks;
}
