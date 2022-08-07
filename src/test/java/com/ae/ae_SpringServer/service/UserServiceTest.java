package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    UserService userService;

    //3-1
    @Test
    public void 회원정보조회(){
        //given
        //유저 주입 : 가입시 회원 정보
        User user = new User();
        String name = "김박사";
        int sex = 1;
        int age = 30;
        String height = "170";
        String weight = "50";
        int icon =3;
        int activity = 40;
        user.setName(name);
        user.setGender(sex);
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);
        user.setIcon(icon);
        user.setActivity(activity);
        userService.create(user);

        //when 유저id로 조회 시
        userService.findOne(user.getId());

        //then 기입한 유저정보와 같아야한다.
        assertEquals(name,user.getName());
        assertEquals(sex,user.getGender());
        assertEquals(age,user.getAge());
        assertEquals(height,user.getHeight());
        assertEquals(weight,user.getWeight());
        assertEquals(icon,user.getIcon());
        assertEquals(activity,user.getActivity());
    }

    //3-2   : 현재 세팅시 null로 권장 칼,탄단지가 들어가고있으므로 코드 수정 후 다시 테스트 시도할 것
    @Test
    public void 회원정보수정(){
        //given
        //유저 주입 : 가입시 회원 정보
        User user = new User();
        user.setName("김김김");
        user.setGender(1);
        user.setAge(50);
        user.setHeight("185");
        user.setWeight("70");
        user.setIcon(2);
        user.setActivity(40);
        userService.create(user);
        String orginRcal = user.getRcal();
        String orginRcarb = user.getRcarb();
        String orginRpro = user.getRpro();
        String orginRfat = user.getRfat();

        //when
        //유저 정보 변화(나이, 키, 체중, 활동량)
        UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
        requestDto.setAge(71);
        requestDto.setHeight("190");
        requestDto.setWeight("80");
        requestDto.setActivity(33);
        userService.update(user.getId(), requestDto);

        //then
        //경계값을 벗어나는 권장 칼로리, 탄단지가 변화해야한다.
        assertNotEquals(orginRcal,user.getRcal());
        assertNotEquals(orginRcarb,user.getRcarb());
        assertNotEquals(orginRpro,user.getRpro());
        assertNotEquals(orginRfat,user.getRfat());

    }
}
