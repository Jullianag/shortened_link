package org.meuprojeto.shortened_link.dtos;

import org.meuprojeto.shortened_link.entities.Link;

public class CreateLinkDTO {

    private String originalUrl;
    private String slug;

    public CreateLinkDTO() {
    }

    public CreateLinkDTO(String originalUrl, String slug) {
        this.originalUrl = originalUrl;
        this.slug = slug;
    }

    public CreateLinkDTO(Link entity) {
        originalUrl = entity.getOriginalUrl();
        slug = entity.getSlug();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getSlug() {
        return slug;
    }
}
