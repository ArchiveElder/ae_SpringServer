package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<User> findByKakao(String kakao) {
        List<User> user = em.createQuery("select u from User u where u.kakao = :kakao", User.class)
                .setParameter("kakao", kakao)
                .getResultList();
        return user.stream().findAny();
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


    public void updateSome(Long id, Record record) {
        em.createQuery("update User u set u.badge_num = u.badge_num + 1, u.remain_calory = u.remain_calory - :cal, " +
                "u.remain_carb = u.remain_carb - :carb, u.remain_pro = u.remain_pro - :pro, u.remain_fat = u.remain_fat - :fat where u.user_id = :id")
                .setParameter("cal", record.getCal())
                .setParameter("carb", record.getCarb())
                .setParameter("pro", record.getProtein())
                .setParameter("fat", record.getFat())
                .executeUpdate();
        em.clear();
    }
*/
    public void updateSome(Long id, Record record) {
        em.createQuery("update User u set u.rcalory = u.rcalory - :cal, " +
                        "u.rcarb = u.rcarb - :carb, u.rpro = u.rpro - :pro, u.rfat = u.rfat - :fat where u.id = :id")
                .setParameter("cal", record.getCal())
                .setParameter("carb", record.getCarb())
                .setParameter("pro", record.getProtein())
                .setParameter("fat", record.getFat())
                .executeUpdate();
        em.clear();
    }
    public int findBadge(Long id) {
        String s = em.createQuery("select u.badge_num from User u where u.user_id = :id")
                .setParameter("id", id)
                .getSingleResult()
                .toString();
        int num = Integer.parseInt(s);
        return num;
    }
}
