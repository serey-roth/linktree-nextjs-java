package com.linktreeclone.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linktreeclone.api.model.Link;

import jakarta.transaction.Transactional;

public interface LinkRepository extends JpaRepository<Link, Long>  {
    List<Link> findAllByCreatorId(Long creatorId);

    @Transactional
    void deleteByCreatorId(Long creatorId);
}
