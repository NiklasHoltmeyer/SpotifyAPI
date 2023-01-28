package de.holtmeyer.niklas.spotify.endpoint.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final Throwable throwable;
    private final int httpStatus;
    private final ZonedDateTime timestamp;
}
