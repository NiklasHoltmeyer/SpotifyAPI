package de.holtmeyer.niklas.spotify.data.entity.io.response;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;
import kong.unirest.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class PagableResponseReduced<T extends Pageable, I> extends Response<List<I>> {
    public PagableResponseReduced(ResponseList<? extends T> responseList) { // List<Response>
        var items = responseList
                .getBody()
                .stream()
                .map(Pageable::getItems)
                .flatMap(Arrays::stream)
                .map(this::cast)
                .toList();

        var statusCode = responseList.getHttpResponse().stream().findAny().map(HttpResponse::getStatus).orElse(404);
        this.setSuccess(statusCode);

        if(items.isEmpty() && !this.isSuccess()){
            items = null;
        }

        this.setBody(Optional.ofNullable(items));
    }

    @SuppressWarnings("unchecked")
    private I cast(Object i){
        if(i != null){
            return (I) i;
        }
        throw new RuntimeException("TODO 87645");
    }
}
