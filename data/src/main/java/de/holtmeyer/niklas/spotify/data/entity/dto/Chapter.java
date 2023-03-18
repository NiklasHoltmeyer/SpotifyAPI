package de.holtmeyer.niklas.spotify.data.entity.dto;

import de.holtmeyer.niklas.spotify.data.entity.dto.common.Pagable;
import lombok.Data;

@Data
public class Chapter extends Pagable {
    Object[] items; //TODO <- dont care rn # https://developer.spotify.com/documentation/web-api/reference/#/operations/get-an-audiobook
}
