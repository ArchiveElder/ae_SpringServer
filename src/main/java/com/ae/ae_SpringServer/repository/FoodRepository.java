package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FoodRepository {
    private final EntityManager em;

    public List<Food> findAll() {
        return em.createQuery("select f from Food f", Food.class)
                .getResultList();
    }

    public List<Food> findFood(Long id) {
        return em.createQuery("select f from Food f where f.id = :param", Food.class)
                .setParameter("param", id)
                .getResultList();
    }
}
