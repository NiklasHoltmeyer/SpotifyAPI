package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.pagable.PlaylistsWithMinimalTrackInfos;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import de.holtmeyer.niklas.spotify.data.entity.io.response.PagableResponseReduced;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.PlaylistEndpoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrentUserPlaylistService {
    PlaylistAPI api;
    AccessToken accessToken;

    public Optional<PlaylistsWithMinimalTrackInfo> findByName(String playlistName) {
        return this.api.getCurrentUserPlaylists()
                .getBody()
                .get()
                .stream()
                .filter(x->x.getName().contains(playlistName))
                .findFirst();
    }

    public Response<List<PlaylistsWithMinimalTrackInfo>> follows(){
        var url = PlaylistEndpoint.ME;
        var responseList = SpotifyGetRequest.<PlaylistsWithMinimalTrackInfos>builder()
                .url(url)
                .responseClass(PlaylistsWithMinimalTrackInfos.class)
                .apiToken(accessToken)
                .build()
                .executeAll();
        return new PagableResponseReduced<PlaylistsWithMinimalTrackInfos, PlaylistsWithMinimalTrackInfo>(responseList);
    }
}
