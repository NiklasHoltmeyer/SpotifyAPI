package de.holtmeyer.niklas.spotify.data.service.spotify.api.track;

import de.holtmeyer.niklas.spotify.data.entity.dto.pagable.UserSavedTracks;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.PagableResponseReduced;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.TrackEndpoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TrackAPI {
    AccessToken accessToken;

    public Response<List<UserSavedTrack>> getCurrentSavedTracks(){
        var url = TrackEndpoint.ME;

        var queryParameter = Map.<String, Object>of(
                "limit", 50
        );

        var responseList = SpotifyGetRequest.<UserSavedTracks>builder()
                .url(url)
                .responseClass(UserSavedTracks.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .executeAll();

        return new PagableResponseReduced<UserSavedTracks, UserSavedTrack>(responseList);
    }
}