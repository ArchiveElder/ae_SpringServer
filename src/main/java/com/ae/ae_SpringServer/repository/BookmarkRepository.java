package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    private final EntityManager em;

    public void save(Bookmark bookmark) {
        em.persist(bookmark);
    }


    public List<Bistro> findBookmark(Long id) {
        return em.createQuery("select b from Bistro b" +
                " where b.id IN" +
                " (select bm.bistro from Bookmark bm join bm.user u where u.id = :param)", Bistro.class)
                .setParameter("param", id)
                .getResultList();

    }

}
