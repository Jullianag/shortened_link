package org.meuprojeto.shortened_link.dtos;

import org.meuprojeto.shortened_link.entities.Link;

public class CreateLinkDTO {

    private String originalUrl;
    private String newLink;

    public CreateLinkDTO() {
    }

    public CreateLinkDTO(String originalUrl, String newLink) {
        this.originalUrl = originalUrl;
        this.newLink = newLink;
    }

    public CreateLinkDTO(Link entity) {
        originalUrl = entity.getOriginalUrl();
        newLink = entity.getNewLink();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getNewLink() {
        return newLink;
    }
}
