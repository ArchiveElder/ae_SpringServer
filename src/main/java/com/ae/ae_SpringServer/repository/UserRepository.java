package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) { em.persist(user); }

    public User findOne(Long id) { return em.find(User.class, id); }

    public List<User> findAll() {
        return em.createQuery("select u from user u", User.class)
                .getResultList();
    }

    public List<User> findByName(String name) {
        return em.createQuery("select u from user u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    /*
    public int updateOne(Long id) {
        String s;
        em.createQuery("update user u set u.badge_num = u.badge_num + 1 where u.user_id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.clear();
        s = em.createQuery("select u.badge_num from user u where u.user_id = :id")
                .setParameter("id", id)
                .getSingleResult()
                .toString();
        return Integer.parseInt(s);
    }
     */

    public void updateSome(Long id, Record record) {
        em.createQuery("update user u set u.badge_num = u.badge_num + 1, u.remain_calory = u.remain_calory - :cal, " +
                "u.remain_carb = u.remain_carb - :carb, u.remain_pro = u.remain_pro - :pro, u.remain_fat = u.remain_fat - :fat where u.user_id = :id")
                .setParameter("cal", record.getCalory())
                .setParameter("carb", record.getCarb())
                .setParameter("pro", record.getProtein())
                .setParameter("fat", record.getFat())
                .executeUpdate();
        em.clear();
    }

    public int findBadge(Long id) {
        String s = em.createQuery("select u.badge_num from user u where u.user_id = :id")
                .setParameter("id", id)
                .getSingleResult()
                .toString();
        int num = Integer.parseInt(s);
        return num;
    }
}
