package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
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


    public List<BistroV2> findBookmark(Long userId) {
        return em.createQuery("select b from BistroV2 b" +
                " where b.id IN" +
                " (select bm.bistro from Bookmark bm join bm.user u where u.id = :param)", BistroV2.class)
                .setParameter("param", userId)
                .getResultList();

    }

    public Long findBookmarkById(Long userId, Long bistroId) {
        Bookmark bookmark = em.createQuery("select bm from Bookmark bm" +
                        " where bm.bistro.id = :bistroId" +
                        " and bm.user.id = :userId", Bookmark.class)
                .setParameter("bistroId", bistroId)
                .setParameter("userId", userId)
                .getSingleResult();
        return bookmark.getId();
    }

    public Long deleteBoomark(Long bookmarkId) {
        em.createQuery("delete from Bookmark b where b.id = :param")
                .setParameter("param", bookmarkId)
                .executeUpdate();
        return bookmarkId;
    }

}
