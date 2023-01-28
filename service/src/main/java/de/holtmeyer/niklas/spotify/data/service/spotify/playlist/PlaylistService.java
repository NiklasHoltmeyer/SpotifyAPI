package de.holtmeyer.niklas.spotify.data.service.spotify.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.Playlist;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint.PLAYLIST_BY_ID;

@Service
public class PlaylistService {
    @Autowired
    @Getter
    AccessToken accessToken;

    public Response<? extends Playlist> get(String playlist_id){
        var url = PLAYLIST_BY_ID.formatted(playlist_id);

        return SpotifyGetRequest.<Playlist>builder()
                .url(url)
                .responseClass(Playlist.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }

}
