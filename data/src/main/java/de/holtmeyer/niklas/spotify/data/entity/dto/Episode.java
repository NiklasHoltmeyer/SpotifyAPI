package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class Episode {
    String audio_preview_url;
    String description;
    String html_description;
    Integer duration_ms;
    Boolean explicit;
    ExternalUrls external_urls;
    String href;
    String id;
    Image[] images;
    Boolean is_externally_hosted;
    Boolean is_playable;
    String language;
    String[] languages;
    String name;
    String release_date;
    String release_date_precision;
    EpisodeResumePoint resume_point;
    String type;
    String uri;
    Restrictions restrictions;
    Show show;
}
