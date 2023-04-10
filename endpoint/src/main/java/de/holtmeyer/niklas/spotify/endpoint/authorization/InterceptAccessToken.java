package de.holtmeyer.niklas.spotify.endpoint.authorization;

import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component @AllArgsConstructor
public class InterceptAccessToken implements HandlerInterceptor {
    AccessToken accessToken;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setAccessTokenCookie(request);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void setAccessTokenCookie(HttpServletRequest request) {
        if(request.getCookies() == null) return;

        Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("access_token"))
                .map(Cookie::getValue)
                .findFirst()
                .ifPresent(access_token -> this.accessToken.setAccessToken(access_token));
    }
}
