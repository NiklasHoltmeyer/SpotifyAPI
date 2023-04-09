package de.holtmeyer.niklas.spotify.data.entity.io.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class PlaylistDetailsRequestBody {
    String name;
    @SerializedName("public") @Accessors(fluent = true)
    Boolean isPublic;
    Boolean collaborative;
    String description;
}
