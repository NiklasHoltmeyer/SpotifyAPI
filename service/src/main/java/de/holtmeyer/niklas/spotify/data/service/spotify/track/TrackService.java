package de.holtmeyer.niklas.spotify.data.service.spotify.track;

import de.holtmeyer.niklas.spotify.data.entity.dto.pagable.UserSavedTracks;
import de.holtmeyer.niklas.spotify.data.entity.dto.track.UserSavedTrack;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.ResponseReduced;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.TrackEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    @Autowired
    AccessToken accessToken;

    public Response<List<UserSavedTrack>> getCurrentSaveTracks(){
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

        return new ResponseReduced<UserSavedTracks, UserSavedTrack>(responseList);
    }
}