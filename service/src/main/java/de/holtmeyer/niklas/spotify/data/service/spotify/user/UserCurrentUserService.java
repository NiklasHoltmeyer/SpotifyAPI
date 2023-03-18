package de.holtmeyer.niklas.spotify.data.service.spotify.user;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artists;
import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.UsersTopArtistsResponse;
import de.holtmeyer.niklas.spotify.data.entity.io.response.UsersTopTracksResponse;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.ResponseMapper;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyDeleteRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPutRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint;
import kong.unirest.JsonNode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserCurrentUserService {
    @Autowired
    @Getter
    AccessToken accessToken;

    public Response<? extends UserProfile> getProfile(){
        return SpotifyGetRequest.<UserProfile>builder()
                .url(Endpoint.user.current.ME)
                .responseClass(UserProfile.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }

    public Response<? extends UsersTopArtistsResponse> getTopArtists(String limit, String offset){
        var queryParameter = buildTopResponseQueryParameter(limit, offset);

        return SpotifyGetRequest.<UsersTopArtistsResponse>builder()
                .url(Endpoint.user.current.TOP_ARTISTS)
                .responseClass(UsersTopArtistsResponse.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .execute();
    }

    public Response<? extends UsersTopTracksResponse> getTopTracks(String limit, String offset){
        var queryParameter = buildTopResponseQueryParameter(limit, offset);

        return SpotifyGetRequest.<UsersTopTracksResponse>builder()
                .url(Endpoint.user.current.TOP_TRACKS)
                .responseClass(UsersTopTracksResponse.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build().execute();
    }

    public Response<? extends Artists> getFollowedArtists(String limit, String offset){
        var queryParameter = buildTopResponseQueryParameter(limit, offset);

        return SpotifyGetRequest.<Artists>builder()
                .url(Endpoint.user.current.FOLLOWED_ARTISTS)
                .responseClass(Artists.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build().execute();
    }

    public Response<? extends List<Boolean>> isFollowingArtist(List<String> ids){
        return isFollowingByType(ids, "artist");
    }
    public Response<? extends List<Boolean>> isFollowingUser(List<String> ids){
        return isFollowingByType(ids, "user");
    }
    public Response<? extends List<Boolean>> isFollowingArtist(String ids){
        return isFollowingByType(ids, "artist");
    }
    public Response<? extends List<Boolean>> isFollowingUser(String ids){
        return isFollowingByType(ids, "user");
    }
    private Response<? extends List<Boolean>> isFollowingByType(List<String> ids, String type) {
        String idsCombined = String.join(",", ids);

        return isFollowingByType(idsCombined, type);
    }
    public Response followPlaylist(String playlist_id){
        var url = Endpoint.user.current.FOLLOW_PLAYLIST.formatted(playlist_id);
        return SpotifyPutRequest.builder()
                .url(url)
                .apiToken(accessToken)
                .build()
                .execute();
    }
    public Response unfollowPlaylist(String playlist_id){
        var url = Endpoint.user.current.FOLLOW_PLAYLIST.formatted(playlist_id);
        return SpotifyDeleteRequest.builder()
                .url(url)
                .apiToken(accessToken)
                .build()
                .execute();
    }

    private Response<? extends List<Boolean>> isFollowingByType(String ids, String type) {
        var queryParameter = new HashMap<String, Object>();

        queryParameter.put("ids", ids);
        queryParameter.put("type", type);

        ResponseMapper<JsonNode, List<Boolean>> mapper = (jsonResponse) -> jsonResponse.getBody().getArray().toList();
        return (Response<? extends List<Boolean>>)SpotifyGetRequest.<JsonNode>builder()
                .url(Endpoint.user.current.FOLLOWS_ARTISTS_OR_USERS)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .execute(mapper);
    }
    private HashMap<String, Object> buildTopResponseQueryParameter(String limit, String offset){
        var queryParameter = new HashMap<String, Object>();

        queryParameter.put("limit", limit);
        queryParameter.put("offset", offset);
        queryParameter.put("time_range", "long_term");

        return queryParameter;
    }
}