package de.holtmeyer.niklas.spotify.data.service.authorization.request;

import de.holtmeyer.niklas.spotify.data.service.configuration.ClientConfiguration;
import de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

@Getter
public class RequestAuthorizationRequest {
    String url;
    String response_type;
    String client_id;
    Optional<String> scope;
    String redirect_uri;
    String state;
    ClientConfiguration clientConfiguration;

    public RequestAuthorizationRequest(ClientConfiguration clientConfiguration) {
        Optional<String> scopeEncoded = this.encode(clientConfiguration.getSCOPE());
        this.scope = scopeEncoded;

        this.url = Endpoint.REQUEST_AUTH_CODE;
        this.response_type = "code";
        this.client_id = clientConfiguration.getID();
        this.redirect_uri = clientConfiguration.getREDIRECT_URI();
        this.state = null;
        this.clientConfiguration = clientConfiguration;
    }

    private Optional<String> encode(String string){
        String encoded;
        try {
            encoded = URLEncoder.encode(string, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            encoded = null;
        }

        return Optional.ofNullable(encoded);
    }

    public String build(){
        return UriComponentsBuilder
                .fromHttpUrl(this.getUrl())
                .queryParams(this.toQueryParams())
                .build(true).toString();
    }

    private MultiValueMap<String, String> toQueryParams(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("response_type", this.response_type);
        map.add("response_type", this.response_type);
        map.add("client_id", clientConfiguration.getID());
        map.add("scope", this.scope.get());
        map.add("redirect_uri", clientConfiguration.getREDIRECT_URI());

        if(state != null) map.add("state", this.state);

        return map;
    }

    public void setRandomState(){
        this.state = this.getRandomState(16);
    }

    private static String getRandomState(int length){
        Random random = new Random();
        final int alphabetLength = 'Z' - 'A' + 1;
        StringBuilder result = new StringBuilder(length);

        char charCaseBit;

        while (result.length() < length) {
            charCaseBit = (char) (random.nextInt(2) << 5);
            result.append((char) (charCaseBit | ('A' + random.nextInt(alphabetLength))));
        }
        return result.toString();
    }
}
