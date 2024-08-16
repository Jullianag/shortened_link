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

    @Transactional
    public ResponseLinkDTO createLink(CreateLinkDTO dto) {

        Link link = new Link();
        link.setOriginalUrl(dto.getOriginalUrl());

        var newLink = slugFy(dto.getNewLink());
        var linkWithSlug = linkRepository.findByNewLink(newLink);

        if (newLink.isEmpty() || newLink.isBlank()) {
            newLink = generateCode();
        }

        link.setNewLink(newLink);
        link.setClicks(0);

        link = linkRepository.save(link);

        return new ResponseLinkDTO(link);

    }

    @Transactional
    public ResponseLinkDTO getLinkDetails(String newLink) {
        Link link = getLinkBySlug(newLink);

        return new ResponseLinkDTO(link);
    }

    @Transactional
    public String visitLink(String newLink) {
        Link link = getLinkBySlug(newLink);

        link.setClicks(link.getClicks() + 1);
        link.setMoment(Instant.now());

        link = linkRepository.save(link);

        return link.getOriginalUrl();
    }

    private Link getLinkBySlug(String newLink) {

        Optional<Link> link = linkRepository.findByNewLink(newLink);

        return link.get();
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 4);
    }

    private String slugFy(String newLink) {
        String normalizedSlug = Normalizer.normalize(newLink, Normalizer.Form.NFD);

        return normalizedSlug
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "_")
                .toLowerCase();

    }
}
