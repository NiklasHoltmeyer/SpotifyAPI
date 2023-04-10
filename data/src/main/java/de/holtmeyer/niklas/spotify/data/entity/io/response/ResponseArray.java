package de.holtmeyer.niklas.spotify.data.entity.io.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Data
@NoArgsConstructor(access= AccessLevel.PACKAGE)
public class ResponseArray<MultipleEntities, SingleEntity> extends Response<List<SingleEntity>> {
    public ResponseArray(List<? extends Response<? extends MultipleEntities>> responseList, Function<MultipleEntities, SingleEntity[]> getArrayItems) {
        var items = responseList.stream()
                .map(this::getBody)
                .filter(Objects::nonNull)
                .map(getArrayItems)
                .map(List::of)
                .flatMap(Collection::stream)
                .toList();

        var isRequestSuccess = responseList.stream().map(Response::isSuccess).noneMatch(x-> x == false);

        this.body = Optional.of(items);
        this.success = isRequestSuccess;
    }

    private MultipleEntities getBody(Response<? extends MultipleEntities> reponse){
        if(reponse == null || !reponse.isSuccess()){
            return null;
        }
        return Optional.ofNullable(reponse).flatMap(Response::getBody).orElse(null);
    }
}