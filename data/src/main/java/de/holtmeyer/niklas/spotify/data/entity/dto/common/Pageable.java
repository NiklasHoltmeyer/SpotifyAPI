package de.holtmeyer.niklas.spotify.data.entity.dto.common;

import de.holtmeyer.niklas.spotify.data.entity.dto.Cursors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

@Data
@EqualsAndHashCode(callSuper=true)
public abstract class Pageable<T> extends HasHref {
    T[] items;
    Integer limit;
    String next;
    Integer offset;
    String previous;
    Integer total;
    Cursors cursors;
}
