package de.holtmeyer.niklas.spotify.data.service.authorization;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.request.AccessTokenRequest;
import de.holtmeyer.niklas.spotify.data.service.authorization.request.RefreshTokenRequest;
import de.holtmeyer.niklas.spotify.data.service.authorization.request.RequestAuthorizationRequest;
import de.holtmeyer.niklas.spotify.data.service.authorization.response.RequestAccessTokenResponse;
import de.holtmeyer.niklas.spotify.data.service.configuration.ClientConfiguration;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Optional;

/**
 * Spotify Authorization Code Flow:
 * https://developer.spotify.com/documentation/general/guides/authorization/code-flow/
 */
@Service
public class AuthorizationService {
    @Autowired
    AccessToken accessToken;

    @Autowired
    ClientConfiguration clientConfiguration;

    public RedirectView getLoginRequestAuth(HttpServletResponse servletResponse){
        // Authorization - Step 1 - Request authorization
        RequestAuthorizationRequest request = new RequestAuthorizationRequest(clientConfiguration);
        request.setRandomState();

        Cookie cookie = new Cookie("state", request.getState());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        servletResponse.addCookie(cookie);

        return new RedirectView(request.build());
    }

    public ResponseEntity getLoginCallback(@RequestParam(required=false) Optional<String> code,
                                           @RequestParam(required=false) Optional<String> state,
                                           @RequestParam(required=false) Optional<String> error,
                                           @CookieValue(name = "state") Optional<String> stateCookie,
                                           HttpServletResponse servletResponse) throws IOException {
        // Authorization - Step 2 - Request access and refresh tokens
        if(error.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.get());
        }

        boolean isAuthStateEqual = state.orElse("").equals(stateCookie.orElse(""));
        if(!isAuthStateEqual){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authorize State doesnt match");
        }

        var response = new AccessTokenRequest(code.get(), clientConfiguration).execute();

        addTokenCookies(servletResponse, response);
        servletResponse.sendRedirect("/");

        return null;
    }

    public RedirectView getLoginRequestRefreshAuth(HttpServletResponse servletResponse, String refreshToken, String redirectURI) throws IOException {
        // Authorization - Step 3 - Refresh Token
        var response = new RefreshTokenRequest(refreshToken, clientConfiguration).execute();
        addTokenCookies(servletResponse, response);
        servletResponse.sendRedirect(redirectURI);

        return null;
    }

    private void addTokenCookies(HttpServletResponse servletResponse, Response<RequestAccessTokenResponse> response) {
        var body = response.getBody().get();

        String accessToken = body.getAccess_token();
        String refreshToken = body.getRefresh_token();
        int expires_in_Token = body.getExpires_in() - 10;


        int oneWeek = 60*60*24*7;

        addCookieIfPresent(servletResponse, "access_token", accessToken, expires_in_Token);
        addCookieIfPresent(servletResponse, "refresh_token", refreshToken, oneWeek);
        this.accessToken.setAccessToken(accessToken);
    }

    private void addCookieIfPresent(HttpServletResponse response, String key, String value, Integer maxAge){
        if(value == null || maxAge == null) return;

        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }
}
