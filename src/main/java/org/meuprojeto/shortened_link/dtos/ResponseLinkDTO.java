package org.meuprojeto.shortened_link.dtos;

import org.meuprojeto.shortened_link.entities.Link;

import java.time.Instant;

public class ResponseLinkDTO {

    private String originalUrl;
    private String slug;
    private Integer clicks;
    private Instant moment;

    public ResponseLinkDTO() {
    }

    public ResponseLinkDTO(String originalUrl, String slug, Integer clicks, Instant moment) {
        this.originalUrl = originalUrl;
        this.slug = slug;
        this.clicks = clicks;
        this.moment = moment;
    }

    public ResponseLinkDTO(Link entity) {
        originalUrl = entity.getOriginalUrl();
        slug = entity.getSlug();
        clicks = entity.getClicks();
        moment = entity.getMoment();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getClicks() {
        return clicks;
    }

    public Instant getMoment() {
        return moment;
    }
}
