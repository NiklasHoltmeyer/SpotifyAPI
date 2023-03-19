package de.holtmeyer.niklas.spotify.data.entity.io.response;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ResponseReduced<T extends Pageable, I> extends Response<List<I>> {
    public ResponseReduced(ResponseList<? extends T> responseList) {
        var items = responseList
                .getBody()
                .stream()
                .map(Pageable::getItems)
                .flatMap(Arrays::stream)
                .map(this::cast)
                .toList();

        List<I> body = null;
        if(!items.isEmpty()){
            body = items;
        }

        this.setBody(Optional.ofNullable(body));
        this.setSuccess(this.body.isPresent());
    }

    @SuppressWarnings("unchecked")
    private I cast(Object i){
        if(i != null){
            return (I) i;
        }
        throw new RuntimeException("TODO 87645");
    }


}
