package org.meuprojeto.shortened_link.repositories;

import org.meuprojeto.shortened_link.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findBySlug(String slug);
}
