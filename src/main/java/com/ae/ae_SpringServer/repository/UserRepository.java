package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.SignupRequestDto;
import com.ae.ae_SpringServer.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void signup(Long id, SignupRequestDto dto) {
        int icon = (int)(Math.random() * 13);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        Double rCal = (Double.parseDouble(dto.getHeight()) - 100) * 0.9 * dto.getActivity();
        int gender = dto.getGender(); int age = dto.getAge();
        int pro = 0;
        Double fat, lino, alp, dha;
        fat = lino = alp = dha = 0D;
        // 0이 남성, 1이 여성
        if(gender == 0) {
            if(age >= 6 && age <= 8) {
                pro = 35; lino = 9.0; alp = 1.1; dha = 0.2;
            } else if (age >= 9 && age <= 11) {
                pro = 50; lino = 9.5; alp = 1.3; dha = 0.22;
            } else if (age >= 12 && age <= 14) {
                pro = 60; lino = 12.0; alp = 1.5; dha = 0.23;
            } else if (age >= 15 && age <= 18) {
                pro = 65; lino = 14.0; alp = 1.7; dha = 0.23;
            } else if (age >= 19 && age <= 29) {
                pro = 65; lino = 13.0; alp = 1.6; dha = 0.21;
            } else if (age >= 30 && age <= 49) {
                pro = 65; lino = 11.5; alp = 1.4; dha = 0.4;
            } else if (age >= 50 && age <= 64) {
                pro = 60; lino = 9.0; alp = 1.4; dha = 0.5;
            } else if (age >= 65 && age <= 74) {
                pro = 60; lino = 7.0; alp = 1.2; dha = 0.31;
            } else if (age >= 75) {
                pro = 60; lino = 5.0; alp = 0.9; dha = 0.28;
            }
        } else if(gender == 1) {
            if(age >= 6 && age <= 8) {
                pro = 35; lino = 7.0; alp = 0.8; dha = 0.2;
            } else if (age >= 9 && age <= 11) {
                pro = 45; lino = 9.0; alp = 1.1; dha = 0.15;
            } else if (age >= 12 && age <= 14) {
                pro = 55; lino = 9.0; alp = 1.2; dha = 0.21;
            } else if (age >= 15 && age <= 18) {
                pro = 55; lino = 10.0; alp = 1.1; dha = 0.1;
            } else if (age >= 19 && age <= 29) {
                pro = 55; lino = 10.0; alp = 1.2; dha = 0.15;
            } else if (age >= 30 && age <= 49) {
                pro = 50; lino = 8.5; alp = 1.2; dha = 0.26;
            } else if (age >= 50 && age <= 64) {
                pro = 50; lino = 7.0; alp = 1.2; dha = 0.24;
            } else if (age >= 65 && age <= 74) {
                pro = 50; lino = 4.5; alp = 1.0; dha = 0.15;
            } else if (age >= 75) {
                pro = 50; lino = 3.0; alp = 0.4; dha = 0.14;
            }
        }
        fat = lino + alp + dha;
        em.createQuery("update User u set u.name = :name, u.age = :age, u.gender = :gender, u.height = :height, u.weight = :weight," +
                        "u.date = :date, u.icon = :icon, u.activity = :activity, u.rcal = :calory, u.rcarb = :carb, " +
                        "u.rpro = :pro, u.rfat = :fat " +
                "where u.id = :id")
                .setParameter("name", dto.getName())
                .setParameter("age", age)
                .setParameter("gender", gender)
                .setParameter("height", dto.getHeight())
                .setParameter("weight", dto.getWeight())
                .setParameter("icon", icon)
                .setParameter("activity", dto.getActivity())
                .setParameter("calory", Double.toString(rCal))
                .setParameter("carb", "130")
                .setParameter("pro", Integer.toString(pro))
                .setParameter("fat", Double.toString(fat))
                .setParameter("date", date)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        User u = findOne(id);
        int gender = u.getGender();
        int age = dto.getAge();
        Double rCal = (Double.parseDouble(dto.getHeight()) - 100) * 0.9 * dto.getActivity();
        int pro = 0;
        Double fat, lino, alp, dha;
        fat = lino = alp = dha = 0D;
        // 0이 남성, 1이 여성
        if(gender == 0) {
            if(age >= 6 && age <= 8) {
                pro = 35; lino = 9.0; alp = 1.1; dha = 0.2;
            } else if (age >= 9 && age <= 11) {
                pro = 50; lino = 9.5; alp = 1.3; dha = 0.22;
            } else if (age >= 12 && age <= 14) {
                pro = 60; lino = 12.0; alp = 1.5; dha = 0.23;
            } else if (age >= 15 && age <= 18) {
                pro = 65; lino = 14.0; alp = 1.7; dha = 0.23;
            } else if (age >= 19 && age <= 29) {
                pro = 65; lino = 13.0; alp = 1.6; dha = 0.21;
            } else if (age >= 30 && age <= 49) {
                pro = 65; lino = 11.5; alp = 1.4; dha = 0.4;
            } else if (age >= 50 && age <= 64) {
                pro = 60; lino = 9.0; alp = 1.4; dha = 0.5;
            } else if (age >= 65 && age <= 74) {
                pro = 60; lino = 7.0; alp = 1.2; dha = 0.31;
            } else if (age >= 75) {
                pro = 60; lino = 5.0; alp = 0.9; dha = 0.28;
            }
        } else if(gender == 1) {
            if(age >= 6 && age <= 8) {
                pro = 35; lino = 7.0; alp = 0.8; dha = 0.2;
            } else if (age >= 9 && age <= 11) {
                pro = 45; lino = 9.0; alp = 1.1; dha = 0.15;
            } else if (age >= 12 && age <= 14) {
                pro = 55; lino = 9.0; alp = 1.2; dha = 0.21;
            } else if (age >= 15 && age <= 18) {
                pro = 55; lino = 10.0; alp = 1.1; dha = 0.1;
            } else if (age >= 19 && age <= 29) {
                pro = 55; lino = 10.0; alp = 1.2; dha = 0.15;
            } else if (age >= 30 && age <= 49) {
                pro = 50; lino = 8.5; alp = 1.2; dha = 0.26;
            } else if (age >= 50 && age <= 64) {
                pro = 50; lino = 7.0; alp = 1.2; dha = 0.24;
            } else if (age >= 65 && age <= 74) {
                pro = 50; lino = 4.5; alp = 1.0; dha = 0.15;
            } else if (age >= 75) {
                pro = 50; lino = 3.0; alp = 0.4; dha = 0.14;
            }
        }
        fat = lino + alp + dha;
        em.createQuery("update User u set u.age = :age, u.height = :height, u.weight = :weight, u.activity = :activity," +
                        "u.rcal = :calory, u.rcarb = :carb, u.rpro = :pro, u.rfat = :fat " +
                "where u.id = :id")
                .setParameter("age", dto.getAge())
                .setParameter("height", dto.getHeight())
                .setParameter("weight", dto.getWeight())
                .setParameter("activity", dto.getActivity())
                .setParameter("calory", Double.toString(rCal))
                .setParameter("carb", "130")
                .setParameter("pro", Integer.toString(pro))
                .setParameter("fat", Double.toString(Math.round(fat * 100) / 100.0))
                .setParameter("id", id)
                .executeUpdate();
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
