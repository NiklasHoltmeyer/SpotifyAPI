package de.holtmeyer.niklas.spotify.endpoint.authorization;

import de.holtmeyer.niklas.spotify.data.service.authorization.AuthorizationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Optional;

@RestController
public class AuthController implements AuthAPI {
    @Autowired
    AuthorizationService authorizationService;
    public RedirectView getLogin(HttpServletResponse response,
                                 @CookieValue(name = "refresh_token", defaultValue = "") String refreshToken) throws IOException {
        if(refreshToken.isBlank()){
            // Authorization - Step 1 - Request authorization
            return authorizationService.getLoginRequestAuth(response);
        }

        // Authorization - Step 3 - Refresh Token
        return authorizationService.getLoginRequestRefreshAuth(response, refreshToken, "/");
    }

    public ResponseEntity getLoginCallback(@RequestParam(required=false) Optional<String> code,
                                           @RequestParam(required=false) Optional<String> state,
                                           @RequestParam(required=false) Optional<String> error,
                                           @CookieValue(name = "state") Optional<String> stateCookie,
                                           HttpServletResponse servletResponse) throws IOException {
        return authorizationService.getLoginCallback(code, state, error, stateCookie, servletResponse);
    }
}

