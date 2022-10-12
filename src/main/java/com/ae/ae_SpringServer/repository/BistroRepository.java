package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BistroRepository {
    private final EntityManager em;

    public BistroV2 findOne(Long id) { return em.find(BistroV2.class, id);  }

    public List<BistroV2> getMiddle(String wide) {
        return em.createQuery("select b from BistroV2 b where b.wide = :wide group by b.middle", BistroV2.class)
                .setParameter("wide", wide)
                .getResultList();
    }

    public List<BistroV2> getCategoryList(String wide, String middle) {
        return em.createQuery("select b from BistroV2 b where b.wide = :wide and b.middle = :middle", BistroV2.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<BistroV2> getCategories(String wide, String middle) {
        return em.createQuery("select b from BistroV2 b where b.wide = :wide and b.middle = :middle group by b.category", BistroV2.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<BistroV2> getBistro() {
        return em.createQuery("select b from BistroV2 b", BistroV2.class)
                .getResultList();
    }
}
