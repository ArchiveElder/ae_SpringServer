package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.domain.Recommend;
import com.ae.ae_SpringServer.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecommendRepository {
    private final EntityManager em;

    public void save(Recommend recommend) { em.persist(recommend); }

    // 1개 추천하는 로직

    public Food findRecommSome(Long id) {
        User user = em.find(User.class, id);
        Double cal = Double.valueOf(user.getRcalory());
        Double carb = Double.valueOf(user.getRcarb());
        Double pro = Double.valueOf(user.getRpro());
        Double fat = Double.valueOf(user.getRfat());

        List<Food> foodList = em.createQuery("select f from Food f where f.calory <= :cal and f.carb <= :carb and f.pro <= :pro and f.fat <= :fat")
                .setParameter("cal", cal)
                .setParameter("carb", carb)
                .setParameter("pro", pro)
                .setParameter("fat", fat)
                .getResultList();

        int s = foodList.size();
        int num = (int)(Math.random() * (s-1));

        return foodList.get(num);
    }
}
