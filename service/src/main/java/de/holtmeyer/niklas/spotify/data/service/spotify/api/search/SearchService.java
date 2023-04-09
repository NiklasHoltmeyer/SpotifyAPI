package de.holtmeyer.niklas.spotify.data.service.spotify.api.search;

import de.holtmeyer.niklas.spotify.data.entity.dto.SearchResult;
import de.holtmeyer.niklas.spotify.data.entity.dto.SearchType;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.configuration.Endpoint;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    @Getter
    AccessToken accessToken;
//TODO UNGETESTET -> Data nicht feddisch!
    public Response<? extends SearchResult> search(String query, List<SearchType> types, String include_external, String limit, String offset){
        //TODO types empty or include_external not empty AND NOT eq. audio -> ERROR
        //market -> will be set to the country associated to the current user
        String type = types.stream().map(SearchType::getType).collect(Collectors.joining( "," ));

        var queryParameter = new HashMap<String, Object>(){{
            put("q", query);
            put("type", type);
            put("limit", limit);
            put("offset", offset);
        }};

        if(!include_external.isBlank()){
            queryParameter.put("include_external", include_external);
        }

        var url = Endpoint.search.ITEM;

        return SpotifyGetRequest.<SearchResult>builder()
                .url(url)
                .responseClass(SearchResult.class) //TODO
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .execute();
    }
}
