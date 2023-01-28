package de.holtmeyer.niklas.spotify.data.entity.io.response;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import lombok.Data;

@Data
public class UsersTopArtistsResponse extends UsersTopItemsResponse<Artist>{
    String href;
    Artist[] items;
    Integer limit;
    String next;
    Integer offset;
    String previous;
    Integer total;

    public UsersTopArtistsResponse(UsersTopArtistsResponse response) {
        this.href = response.href;
        this.items = response.items;
        this.limit = response.limit;
        this.next = response.next;
        this.offset = response.offset;
        this.previous = response.previous;
        this.total = response.total;
    }

    public UsersTopArtistsResponse(UsersTopArtistsResponse response, Artist[] items){
        this(response);
        this.items = items;
        this.total = items.length;
    }
}
