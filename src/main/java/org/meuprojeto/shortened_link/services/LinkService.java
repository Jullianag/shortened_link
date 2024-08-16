package org.meuprojeto.shortened_link.services;

import org.meuprojeto.shortened_link.dtos.CreateLinkDTO;
import org.meuprojeto.shortened_link.dtos.ResponseLinkDTO;
import org.meuprojeto.shortened_link.entities.Link;
import org.meuprojeto.shortened_link.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Transactional(readOnly = true)
    public ResponseLinkDTO createLink(CreateLinkDTO dto) {

        Link link = new Link();
        link.setOriginalUrl(dto.getOriginalUrl());

        var slug = slugFy(dto.getSlug());
        var linkWithSlug = linkRepository.findBySlug(slug);

        if (slug.isEmpty() || slug.isBlank()) {
            slug = generateCode();
        }

        link.setSlug(slug);
        link.setClicks(0);

        link = linkRepository.save(link);

        return new ResponseLinkDTO(link);

    }

    @Transactional
    public ResponseLinkDTO getLinkDetails(String slug) {
        Link link = getLinkBySlug(slug);

        return new ResponseLinkDTO(link);
    }

    public String visitLink(String slug) {
        Link link = getLinkBySlug(slug);

        link.setClicks(link.getClicks() + 1);
        link.setMoment(Instant.now());

        link = linkRepository.save(link);

        return link.getOriginalUrl();
    }

    private Link getLinkBySlug(String slug) {

        Optional<Link> link = linkRepository.findBySlug(slug);

        return link.get();
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 4);
    }

    private String slugFy(String slug) {
        String normalizedSlug = Normalizer.normalize(slug, Normalizer.Form.NFD);

        return normalizedSlug
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "_")
                .toLowerCase();

    }
}
