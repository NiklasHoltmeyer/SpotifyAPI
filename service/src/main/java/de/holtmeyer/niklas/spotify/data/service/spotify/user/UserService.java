package de.holtmeyer.niklas.spotify.data.service.spotify.user;

import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyPutRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint.*;

@Service
public class UserService {
    @Autowired @Getter
    AccessToken accessToken;

    @Autowired
    public UserCurrentUserService current;

    /**
     * https://developer.spotify.com/documentation/web-api/reference/#/operations/get-current-users-profile
     * Todo: CheckIfUserFollowsArtists/User fehlerhaft (Return Boolean.Class mag nicht)
     * Check if Users Follow Playlist nicht gemacht
     *
     * alle nicht GET Requests todo
     */
    public Response<? extends UserProfile> get(String user_id){
        var url = USERS_USER_IDS_PROFILE.formatted(user_id);

        return SpotifyGetRequest.<UserProfile>builder()
                .url(url)
                .responseClass(UserProfile.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }

    public Response<? extends String> followPlaylist(String playlist_id){
        var url = USERS_USER_FOLLOW_PLAYLIST.formatted(playlist_id);
        return SpotifyPutRequest.<String>builder()
                .url(url)
                .apiToken(accessToken)
                .build()
                .execute();
    }
}
