package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaylistService {
    public PlaylistAPI api;
    public CurrentUserPlaylistService current;
    public PlaylistList list;

    // todo actions remove -> api access only protected?
    public void deleteAllTracks(String playlist_id){
        var trackuris = this.list.tracks(playlist_id);

        if(trackuris.isEmpty()){
            return;
        }

        this.api.removeTracks(playlist_id, trackuris);
    }

    public int copyAllTracks(String playlist_src_id, String playlist_dst_id, boolean shuffle){
        // TODO zu spezifisch -> löschen
        var trackURIs = this.list.tracks(playlist_src_id);

        if(shuffle){
            Collections.shuffle(trackURIs);
        }

        this.api.addTracks(playlist_dst_id, trackURIs);

        return trackURIs.size();
    }
}
