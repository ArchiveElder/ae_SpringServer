package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    private final EntityManager em;
    public void save(Bookmark bookmark) {
        em.persist(bookmark);
    }
}
