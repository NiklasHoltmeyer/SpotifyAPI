package de.holtmeyer.niklas.spotify.data.service.spotify.user;

import de.holtmeyer.niklas.spotify.data.entity.dto.Artists;
import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.UsersTopArtistsResponse;
import de.holtmeyer.niklas.spotify.data.entity.io.response.UsersTopTracksResponse;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.ResponseMapper;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPutRequest;
import kong.unirest.JsonNode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint.*;
import static de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint.USERS_CURRENT_USERS_FOLLOWED_ARTISTS;

@Service
public class UserCurrentUserService {
    @Autowired
    @Getter
    AccessToken accessToken;

    public Response<? extends UserProfile> getProfile(){
        return SpotifyGetRequest.<UserProfile>builder()
                .url(USERS_CURRENT_USERS_PROFILE)
                .responseClass(UserProfile.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }

    public Response<? extends UsersTopArtistsResponse> getTopArtists(String limit, String offset){
        var queryParameter = buildTopResponseQueryParameter(limit, offset);

        return SpotifyGetRequest.<UsersTopArtistsResponse>builder()
                .url(USERS_CURRENT_USERS_TOP_ARTISTS)
                .responseClass(UsersTopArtistsResponse.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .execute();
    }

    public Response<? extends UsersTopTracksResponse> getTopTracks(String limit, String offset){
        var queryParameter = buildTopResponseQueryParameter(limit, offset);

        return SpotifyGetRequest.<UsersTopTracksResponse>builder()
                .url(USERS_CURRENT_USERS_TOP_TRACKS)
                .responseClass(UsersTopTracksResponse.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build().execute();
    }

    public Response<? extends Artists> getFollowedArtists(String limit, String offset){
        var queryParameter = buildTopResponseQueryParameter(limit, offset);

        return SpotifyGetRequest.<Artists>builder()
                .url(USERS_CURRENT_USERS_FOLLOWED_ARTISTS)
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

    private void followPlaylist(String id){
        var url = String.format(USERS_CURRENT_USER_FOLLOW_PLAYLIST, id);
        SpotifyPutRequest.<JsonNode>builder().url(url)
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
                .url(USERS_CURRENT_CHECK_IF_USER_FOLLOWS_ARTISTS_OR_USERS)
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
/*
* public Response followPlaylist(String ids) {
public Response unfollowPlaylist(String ids) {
public Response followArtists(List<String> ids) {
public Response followUsers(List<String> ids) {
public Response unfollowArtists(List<String> ids) {
*
*
*  */