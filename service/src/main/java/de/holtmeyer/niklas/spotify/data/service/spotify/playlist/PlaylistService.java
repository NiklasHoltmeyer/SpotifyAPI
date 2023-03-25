package de.holtmeyer.niklas.spotify.data.service.spotify.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PlaylistService {
    @Autowired public PlaylistAPI api;
    @Autowired public CurrentUserPlaylistService current;

    public List<PlaylistTrack> listTracks(String playlist_id) {
        return this.api.getTracks(playlist_id, null, null, null)
                .getBody()
                .orElse(Collections.emptyList())
                .stream()
                .map(x->x.getTrack())// Methode Reference does not work sadly
//                .map(HasHref.class::cast)
                .toList();
    }

    public void deleteAllTracks(String playlist_id){
        var trackuris = this.listTracks(playlist_id);

        if(trackuris.isEmpty()){
            return;
        }

        this.api.removeTracks(playlist_id, trackuris);
    }

    public void copyAllTracks(String playlist_src_id, String playlist_dst_id, boolean shuffle){
        // TODO zu spezifisch -> löschen
        var trackURIs = this.listTracks(playlist_src_id);

        if(shuffle){
            Collections.shuffle(trackURIs);
        }

        this.api.addTracks(playlist_dst_id, trackURIs);
    }
}
