package de.holtmeyer.niklas.spotify.data.service.spotify.api.user;

import de.holtmeyer.niklas.spotify.data.entity.dto.UserProfile;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired @Getter
    AccessToken accessToken;

    @Autowired
    public UserCurrentUserService current;
    public Response<? extends UserProfile> get(String user_id){
        var url = Endpoint.user.BY_ID.formatted(user_id);

        return SpotifyGetRequest.<UserProfile>builder()
                .url(url)
                .responseClass(UserProfile.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }
}
