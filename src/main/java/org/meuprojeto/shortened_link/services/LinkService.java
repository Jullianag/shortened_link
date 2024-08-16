package org.meuprojeto.shortened_link.services;

import org.meuprojeto.shortened_link.dtos.CreateLinkDTO;
import org.meuprojeto.shortened_link.dtos.ResponseLinkDTO;
import org.meuprojeto.shortened_link.entities.Link;
import org.meuprojeto.shortened_link.repositories.LinkRepository;
import org.meuprojeto.shortened_link.services.exceptions.ResourceAlreadyExistsException;
import org.meuprojeto.shortened_link.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (linkWithSlug.isPresent()) {
            throw new ResourceAlreadyExistsException("O nome para este link já existe");
        }

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

    @Transactional(readOnly = true)
    public Page<ResponseLinkDTO> findAllPaged(Pageable pageable) {
        Page<Link> list = linkRepository.findAll(pageable);
        return list.map(x -> new ResponseLinkDTO(x));
    }

    @Transactional
    public void deleteLink(Long id) {
        if (!linkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id do link não foi encontrado");
        }

        linkRepository.deleteById(id);
    }

    private Link getLinkBySlug(String newLink) {

        Optional<Link> link = linkRepository.findByNewLink(newLink);

        if (link.isEmpty()) {
            throw new ResourceNotFoundException("O link não existe");
        }

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
