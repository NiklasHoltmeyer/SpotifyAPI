package de.holtmeyer.niklas.spotify.data.service.spotify.api.playlist;

import de.holtmeyer.niklas.spotify.data.entity.dto.Playlist;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.HasHref;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.Snapshot;
import de.holtmeyer.niklas.spotify.data.entity.dto.common.URI;
import de.holtmeyer.niklas.spotify.data.entity.dto.pagable.PlaylistTrackInfos;
import de.holtmeyer.niklas.spotify.data.entity.dto.pagable.PlaylistsWithMinimalTrackInfos;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistTrackInfo;
import de.holtmeyer.niklas.spotify.data.entity.dto.playlist.PlaylistsWithMinimalTrackInfo;
import de.holtmeyer.niklas.spotify.data.entity.io.request.PlaylistDetailsRequestBody;
import de.holtmeyer.niklas.spotify.data.entity.io.request.TrackDeleteRequestBody;
import de.holtmeyer.niklas.spotify.data.entity.io.request.TrackReorderRequestBody;
import de.holtmeyer.niklas.spotify.data.entity.io.request.UrisRequestBody;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.PagableResponseReduced;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyDeleteRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPostRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPutRequest;
import de.holtmeyer.niklas.spotify.data.service.common.util.ListUtil;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.PlaylistEndpoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Access Spotify-Playlist Endpoints
 */
@Service
@AllArgsConstructor
public class PlaylistAPI {
    AccessToken accessToken;
    public Response<? extends Playlist> get(String playlist_id){
        var url = PlaylistEndpoint.BY_ID.formatted(playlist_id);

        return SpotifyGetRequest.<Playlist>builder()
                .url(url)
                .responseClass(Playlist.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }

    public Response<List<PlaylistTrackInfo>> getTracks(String playlist_id, @Nullable String additional_types, @Nullable String fields, @Nullable String market){
        var url = PlaylistEndpoint.TRACKS_BY_ID.formatted(playlist_id);

        var queryParameter = new HashMap<String, Object>();

        if(additional_types != null) queryParameter.put("additional_types", additional_types);
        if(fields != null) queryParameter.put("fields", fields);
        if(market != null) queryParameter.put("market", market);
        queryParameter.put("limit", 100);
        queryParameter.put("offset", 0);

        var responseList = SpotifyGetRequest.<PlaylistTrackInfos>builder()
                .url(url)
                .responseClass(PlaylistTrackInfos.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .executeAll();

        return new PagableResponseReduced<PlaylistTrackInfos, PlaylistTrackInfo>(responseList);
    }

    public List<? extends Response<? extends Snapshot>> removeTracks(String playlist_id, List<? extends HasHref> tracks){
        var url = PlaylistEndpoint.TRACKS_BY_ID.formatted(playlist_id);
        var uriObjects = tracks.stream().filter(Objects::nonNull).map(HasHref::getUri).map(URI::new).toList();

        return ListUtil.batches(uriObjects, 100)
                .map(TrackDeleteRequestBody::new)
                .map(
                        requestBody ->
                                SpotifyDeleteRequest.<Snapshot>builder()
                                .url(url)
                                .responseClass(Snapshot.class)
                                .apiToken(accessToken)
                                .body(requestBody)
                                .build()
                                .execute()
                )
                .toList();
    }

    public List<? extends Response<? extends Snapshot>> addTracks(String playlist_id, List<? extends HasHref> tracks){
        var url = PlaylistEndpoint.TRACKS_BY_ID.formatted(playlist_id);
        var uriObjects = tracks.stream().map(HasHref::getUri).toList();

        return ListUtil.batches(uriObjects, 100)
                .map(UrisRequestBody::new)
                .map(
                        requestBody ->
                                SpotifyPostRequest.<Snapshot>builder()
                                        .url(url)
                                        .responseClass(Snapshot.class)
                                        .apiToken(accessToken)
                                        .body(requestBody)
                                        .build()
                                        .execute()
                )
                .toList();
    }

    public Response<List<PlaylistsWithMinimalTrackInfo>> getCurrentUserPlaylists(){
        var url = PlaylistEndpoint.ME;
        var responseList = SpotifyGetRequest.<PlaylistsWithMinimalTrackInfos>builder()
                .url(url)
                .responseClass(PlaylistsWithMinimalTrackInfos.class)
                .apiToken(accessToken)
                .build()
                .executeAll();
        return new PagableResponseReduced<PlaylistsWithMinimalTrackInfos, PlaylistsWithMinimalTrackInfo>(responseList);

    }

    public  Response<List<PlaylistsWithMinimalTrackInfo>> getUserPlaylists(String user_id){
        var url = PlaylistEndpoint.BY_USER_ID.formatted(user_id);

        var queryParam = Map.<String, Object>of(
                "limit", 50,
                "offset", 0
        );

        var responseList = SpotifyGetRequest.<PlaylistsWithMinimalTrackInfos>builder()
                .url(url)
                .responseClass(PlaylistsWithMinimalTrackInfos.class)
                .apiToken(accessToken)
                .queryParameter(queryParam)
                .build()
                .executeAll();

        return new PagableResponseReduced<PlaylistsWithMinimalTrackInfos, PlaylistsWithMinimalTrackInfo>(responseList);
    }

    /**
     * Either reorder or replace items in a playlist depending on the request's parameters.
     * To reorder items, include range_start, insert_before, range_length and snapshot_id in the request's body.
     * To replace items, include uris as either a query parameter or in the request's body. Replacing items in a
     * playlist will overwrite its existing items. This operation can be used for replacing or clearing items in a
     * playlist.
     *
     * Note: Replace and reorder are mutually exclusive operations which share the same endpoint,
     * but have different parameters. These operations can't be applied together in a single request.
     *
     * https://developer.spotify.com/documentation/web-api/reference/#/operations/reorder-or-replace-playlists-tracks
     * -> TODO replace nicht impl!
     * reorder funkt nicht? oder ich verstehe doku falsch
     */
    public Response reorderTracks(String playlist_id, TrackReorderRequestBody body){
        var url = PlaylistEndpoint.UPDATE_TRACKS.formatted(playlist_id);

        return SpotifyPutRequest.<Snapshot>builder()
                .url(url)
                .responseClass(Snapshot.class)
                .apiToken(accessToken)
                .body(body)
                .build()
                .execute();
    }

    public Response setDetails(String playlist_id, PlaylistDetailsRequestBody body){
        var url = PlaylistEndpoint.BY_ID.formatted(playlist_id);

        return SpotifyPutRequest.builder()
                .url(url)
                .responseClass(kong.unirest.Empty.class)
                .apiToken(accessToken)
                .body(body)
                .build()
                .execute();
    }
}
