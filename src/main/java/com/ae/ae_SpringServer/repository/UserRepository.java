package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.CalcNutrientDtos;
import com.ae.ae_SpringServer.dto.request.CalcRequestDto;
import com.ae.ae_SpringServer.dto.request.SignupRequestDto;
import com.ae.ae_SpringServer.dto.request.UserUpdateRequestDto;
import com.ae.ae_SpringServer.dto.request.v3.SignupRequestDtoV3;
import com.ae.ae_SpringServer.utils.CalcNutrients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByKakao(String kakao) {
        List<User> user = em.createQuery("select u from User u where u.kakao = :kakao", User.class)
                .setParameter("kakao", kakao)
                .getResultList();
        return user.stream().findAny();
    }

    public void signup(Long id, SignupRequestDto dto) {
        int age = dto.getAge();
        int gender = dto.getGender();
        String name = dto.getName();
        String weight = dto.getWeight();
        String height = dto.getHeight();
        int activity = dto.getActivity();
        int icon = (int)(Math.random() * 13);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        CalcRequestDto calcRequestDto = new CalcRequestDto(dto.getName(), dto.getAge(),dto.getGender(), dto.getHeight(), dto.getWeight(), dto.getActivity());
        CalcNutrientDtos calcNutrientDtos = CalcNutrients.calcNutrientDtos(calcRequestDto);

        em.createQuery("update User u set u.name = :name, u.age = :age, u.gender = :gender, u.height = :height, u.weight = :weight," +
                        "u.date = :date, u.icon = :icon, u.activity = :activity, u.rcal = :calory, u.rcarb = :carb, " +
                        "u.rpro = :pro, u.rfat = :fat " +
                "where u.id = :id")
                .setParameter("name", name)
                .setParameter("age", age)
                .setParameter("gender", gender)
                .setParameter("height", height)
                .setParameter("weight", weight)
                .setParameter("icon", icon)
                .setParameter("activity", activity)
                .setParameter("calory", calcNutrientDtos.getRcal())
                .setParameter("carb", calcNutrientDtos.getRcarb())
                .setParameter("pro", calcNutrientDtos.getRpro())
                .setParameter("fat", calcNutrientDtos.getRfat())
                .setParameter("date", date)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        User u = findOne(id);
        String name = u.getName();
        int gender = u.getGender();

        CalcRequestDto calcRequestDto = new CalcRequestDto(name, dto.getAge(), gender, dto.getHeight(), dto.getWeight(), dto.getActivity());
        CalcNutrientDtos calcNutrientDtos = CalcNutrients.calcNutrientDtos(calcRequestDto);
        em.createQuery("update User u set u.age = :age, u.height = :height, u.weight = :weight, u.activity = :activity," +
                        " u.rcal = :cal, u.rcarb = :carb, u.rpro = :pro, u.rfat = :fat " +
                "where u.id = :id")
                .setParameter("age", dto.getAge())
                .setParameter("height", dto.getHeight())
                .setParameter("weight", dto.getWeight())
                .setParameter("activity", dto.getActivity())
                .setParameter("cal", calcNutrientDtos.getRcal())
                .setParameter("carb", calcNutrientDtos.getRcarb())
                .setParameter("pro", calcNutrientDtos.getRpro())
                .setParameter("fat", calcNutrientDtos.getRfat())
                .setParameter("id", id)
                .executeUpdate();
    }

    public void delete(Long id) {
        em.createQuery("delete from Record r where r.user = (select u from User u where u.id = :id)")
                        .setParameter("id", id)
                        .executeUpdate();
        em.createQuery("delete from User u where u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Optional<User> findByApple(String appleId) {
        List<User> user = em.createQuery("select u from User u where u.apple = :apple", User.class)
                .setParameter("apple", appleId)
                .getResultList();
        return user.stream().findAny();
    }

    public Long nicknameCheck(String nickname) {
        return (Long) em.createQuery("select count(u.id) from User u where u.nickname = :nickname")
                .setParameter("nickname", nickname)
                .getSingleResult();
    }

    public void signupNickname(Long id, SignupRequestDtoV3 dto) {
        int age = dto.getAge();
        int gender = dto.getGender();
        String nickname = dto.getNickname();
        String weight = dto.getWeight();
        String height = dto.getHeight();
        int activity = dto.getActivity();
        int icon = (int)(Math.random() * 13);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        CalcRequestDto calcRequestDto = new CalcRequestDto(dto.getNickname(), dto.getAge(),dto.getGender(), dto.getHeight(), dto.getWeight(), dto.getActivity());
        CalcNutrientDtos calcNutrientDtos = CalcNutrients.calcNutrientDtos(calcRequestDto);

        em.createQuery("update User u set u.nickname = :nickname, u.age = :age, u.gender = :gender, u.height = :height, u.weight = :weight," +
                        "u.date = :date, u.icon = :icon, u.activity = :activity, u.rcal = :calory, u.rcarb = :carb, " +
                        "u.rpro = :pro, u.rfat = :fat " +
                        "where u.id = :id")
                .setParameter("nickname", nickname)
                .setParameter("age", age)
                .setParameter("gender", gender)
                .setParameter("height", height)
                .setParameter("weight", weight)
                .setParameter("icon", icon)
                .setParameter("activity", activity)
                .setParameter("calory", calcNutrientDtos.getRcal())
                .setParameter("carb", calcNutrientDtos.getRcarb())
                .setParameter("pro", calcNutrientDtos.getRpro())
                .setParameter("fat", calcNutrientDtos.getRfat())
                .setParameter("date", date)
                .setParameter("id", id)
                .executeUpdate();

    }
}
