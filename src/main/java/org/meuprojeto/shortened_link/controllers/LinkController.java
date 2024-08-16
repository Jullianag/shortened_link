package org.meuprojeto.shortened_link.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.meuprojeto.shortened_link.dtos.CreateLinkDTO;
import org.meuprojeto.shortened_link.dtos.ResponseLinkDTO;
import org.meuprojeto.shortened_link.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping
    public ResponseEntity<?> createLink(@RequestBody CreateLinkDTO dto) {
        ResponseLinkDTO response = linkService.createLink(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/ìd}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "{newLink}/details")
    public ResponseEntity<?> getLinkDetails(@PathVariable("newLink") String newLink) {
        ResponseLinkDTO response = linkService.getLinkDetails(newLink);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{newLink}")
    public ResponseEntity<?> visitLink(@PathVariable("newLink") String newLink, HttpServletResponse response) throws IOException {
        var originalUrl = linkService.visitLink(newLink);
        response.sendRedirect(originalUrl);
        return ResponseEntity.status(301).build();
    }

    @GetMapping
    public ResponseEntity<Page<ResponseLinkDTO>> findAll(Pageable pageable) {
        Page<ResponseLinkDTO> list = linkService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}
