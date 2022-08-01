package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IngredientRepository {
    private final EntityManager em;

    public List<Ingredient> findAll() {
        return em.createQuery("select i from Ingredient i", Ingredient.class)
                .getResultList();
    }
}
