package de.holtmeyer.niklas.spotify.endpoint.configuration;

import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.endpoint.authorization.InterceptAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    AccessToken accessToken;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptAccessToken(accessToken));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
