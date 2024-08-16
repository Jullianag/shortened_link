package org.meuprojeto.shortened_link.dtos;

import org.meuprojeto.shortened_link.entities.Link;

import java.time.Instant;

public class ResponseLinkDTO {

    private Long id;
    private String originalUrl;
    private String newLink;
    private Integer clicks;
    private Instant moment;

    public ResponseLinkDTO() {
    }

    public ResponseLinkDTO(Long id, String originalUrl, String newLink, Integer clicks, Instant moment) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.newLink = newLink;
        this.clicks = clicks;
        this.moment = moment;
    }

    public ResponseLinkDTO(Link entity) {
        id = entity.getId();
        originalUrl = entity.getOriginalUrl();
        newLink = entity.getNewLink();
        clicks = entity.getClicks();
        moment = entity.getMoment();
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getNewLink() {
        return newLink;
    }

    public Integer getClicks() {
        return clicks;
    }

    public Instant getMoment() {
        return moment;
    }
}
