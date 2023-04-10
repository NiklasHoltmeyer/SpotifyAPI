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
                .map(Response::getBody)
                .map(x->x.orElse(null))
                .filter(Objects::nonNull)
                .map(getArrayItems)
                .map(List::of)
                .flatMap(Collection::stream)
                .toList();

        var isRequestSucces = responseList.stream().map(Response::isSuccess).noneMatch(x-> x == false);

        this.body = Optional.of(items);
        this.success = isRequestSucces;
    }
}
/*   "release_date" : "2023-03-31",
        "release_date_precision" : "day", */