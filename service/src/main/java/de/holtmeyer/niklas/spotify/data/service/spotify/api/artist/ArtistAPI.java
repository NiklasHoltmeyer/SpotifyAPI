package de.holtmeyer.niklas.spotify.data.service.spotify.api.artist;

import de.holtmeyer.niklas.spotify.data.entity.dto.Album;
import de.holtmeyer.niklas.spotify.data.entity.dto.Artist;
import de.holtmeyer.niklas.spotify.data.entity.dto.artist.ArtistArtists;
import de.holtmeyer.niklas.spotify.data.entity.dto.pagable.Albums;
import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import de.holtmeyer.niklas.spotify.data.entity.io.response.PagableResponseReduced;
import de.holtmeyer.niklas.spotify.data.entity.io.response.ResponseArray;
import de.holtmeyer.niklas.spotify.data.service.authorization.AccessToken;
import de.holtmeyer.niklas.spotify.data.service.common.request.SpotifyGetRequest;
import de.holtmeyer.niklas.spotify.data.service.common.util.ListUtil;
import de.holtmeyer.niklas.spotify.data.service.configuration.endpoint.ArtistEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import de.holtmeyer.niklas.spotify.data.entity.io.request.UrisRequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistAPI {
    @Autowired
    AccessToken accessToken;

    public Response<? extends Artist> get(String artist_id){
        var url = ArtistEndpoint.BY_ID.formatted(artist_id);

        return SpotifyGetRequest.<Artist>builder()
                .url(url)
                .responseClass(Artist.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }

    public Response<List<Artist>> get(List<String> artist_ids){
        var url = ArtistEndpoint.BY_QUERY;

        var responseList = ListUtil.batches(artist_ids, 50)
                .map(UrisRequestBody::new)
                .map(
                        requestBody ->
                                SpotifyGetRequest.<ArtistArtists>builder()
                                        .url(url)
                                        .responseClass(ArtistArtists.class)
                                        .apiToken(accessToken)
                                        .queryParameter(requestBody.toQueryParameter("ids"))
                                        .build()
                                        .execute()
                )
                .toList();

        return new ResponseArray<ArtistArtists, Artist>(responseList, ArtistArtists::getArtists);
    }

    public Response<List<Album>> getAlbums(String artist_id, @Nullable List<String> include_groups, @Nullable String market) {
        var url = ArtistEndpoint.ALBUMS_BY_ID.formatted(artist_id);

        var queryParameter = new HashMap<String, Object>();
        if(include_groups != null && !include_groups.isEmpty()){
            queryParameter.put("include_groups", String.join(",", include_groups));
        }

        if(market != null && !market.isBlank()){
            queryParameter.put("market", market);
        }

        queryParameter.put("limit", 50);
        queryParameter.put("offset", 0);

        var responseList = SpotifyGetRequest.<Albums>builder()
                .url(url)
                .responseClass(Albums.class)
                .apiToken(accessToken)
                .queryParameter(queryParameter)
                .build()
                .executeAll();

        return new PagableResponseReduced<Albums, Album>(responseList);
    }

    public Response<? extends ArtistArtists> getRelated(String artist_id){
        var url = ArtistEndpoint.RELATED_ARTISTS_BY_ID.formatted(artist_id);

        return SpotifyGetRequest.<ArtistArtists>builder()
                .url(url)
                .responseClass(ArtistArtists.class)
                .apiToken(accessToken)
                .build()
                .execute();
    }
}
