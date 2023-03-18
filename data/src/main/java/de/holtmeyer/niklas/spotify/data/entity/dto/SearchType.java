package de.holtmeyer.niklas.spotify.data.entity.dto;

public enum SearchType {
    //Query/Search Type as used in: https://developer.spotify.com/documentation/web-api/reference/#/operations/search
    ALBUM("album"),
    TRACK("track");

    final String type;

    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
