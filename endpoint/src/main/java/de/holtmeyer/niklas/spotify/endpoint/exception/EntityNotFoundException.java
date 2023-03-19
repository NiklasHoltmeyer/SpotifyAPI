package de.holtmeyer.niklas.spotify.endpoint.exception;

public class EntityNotFoundException extends ApiRequestException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
