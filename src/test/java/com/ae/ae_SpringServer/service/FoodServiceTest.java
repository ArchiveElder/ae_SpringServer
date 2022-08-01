package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class FoodServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    FoodService foodService;
    @Autowired
    FoodRepository foodRepository;

    @Test
    public void 전체음식조회() {
        // given

        // when
        List<Food> findFoods = foodService.findAllFoods();

        // then
        assertEquals(findFoods.get(0).getName(), "더덕구이");
        assertEquals(findFoods.stream().count(), 2534);
    }

    @Test
    public void 음식인덱스조회() {
        // given
        Long id = 0L;

        // when
        List<Food> findFood = foodService.findFood(id);

        // then
        if(findFood.size() != 0) {
            assertEquals(findFood.get(0).getId(), id);
        }
    }
}
