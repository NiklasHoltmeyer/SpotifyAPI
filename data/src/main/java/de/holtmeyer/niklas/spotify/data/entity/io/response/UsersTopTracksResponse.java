package de.holtmeyer.niklas.spotify.data.entity.io.response;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import lombok.Data;

import javax.sound.midi.Track;

@Data
public class UsersTopTracksResponse extends UsersTopItemsResponse<Track>{
    String href;
    Track[] items;
    Integer limit;
    String next;
    Integer offset;
    String previous;
    Integer total;

    public UsersTopTracksResponse(UsersTopTracksResponse response) {
        this.href = response.href;
        this.items = response.items;
        this.limit = response.limit;
        this.next = response.next;
        this.offset = response.offset;
        this.previous = response.previous;
        this.total = response.total;
    }

    public UsersTopTracksResponse(UsersTopTracksResponse response, Track[] items){
        this(response);
        this.items = items;
        this.total = items.length;
    }
}
