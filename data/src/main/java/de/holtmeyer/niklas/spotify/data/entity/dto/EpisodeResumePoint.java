package de.holtmeyer.niklas.spotify.data.entity.dto;

import lombok.Data;

@Data
public class EpisodeResumePoint {
    Boolean fully_played;
    Integer resume_position_ms;
}
