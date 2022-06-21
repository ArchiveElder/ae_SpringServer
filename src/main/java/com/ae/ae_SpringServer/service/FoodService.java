package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> findFood(Long id) { return  foodRepository.findFood(id); }


}
