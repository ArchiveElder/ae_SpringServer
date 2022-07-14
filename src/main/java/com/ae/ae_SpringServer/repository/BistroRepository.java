package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BistroRepository {
    private final EntityManager em;

    public List<Bistro> getMiddle(String wide) {
        return em.createQuery("select r from Bistro r where r.wide = :wide", Bistro.class)
                .setParameter("wide", wide)
                .getResultList();
    }
}
