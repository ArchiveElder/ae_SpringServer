package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.domain.Recommend;
import com.ae.ae_SpringServer.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;

    public Long add(Recommend recommend) {
        recommendRepository.save(recommend);
        return recommend.getId();
    }

    public Food findRecommend(Long id) {
        return recommendRepository.findRecommSome(id);
    }
}
