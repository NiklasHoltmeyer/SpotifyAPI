package de.holtmeyer.niklas.spotify.data.service.common.request;

import kong.unirest.HttpResponse;

@FunctionalInterface
public interface ResponseMapper<I, O> {
    O map(HttpResponse<I> request);
}
