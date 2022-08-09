package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.repository.BistroRepository;
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
public class BistroServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    BistroService bistroService;
    @Autowired
    BistroRepository bistroRepository;

    @Test
    public void 전체지도음식점조회() {
        // given

        // when
        List<Bistro> allBistro = bistroService.getBistro();

        // then
        assertEquals(allBistro.get(0).getName(), "채근담 대치점");
        assertEquals(allBistro.stream().count(), 118);
    }

    //6-1
    @Test
    public void 음식점중분류조회() {
        //given 조회하려는 대분류가 주어졌을 경우
        String wide = "서울특별시";

        //when  주어진 대분류에 해당하는 중분류 조회
        Object count = em.createQuery("select COUNT(DISTINCT(b.middle))" +
                        " from Bistro b where b.wide = :wide group by b.wide")
                .setParameter("wide",wide)
                .getSingleResult();
        List<Bistro> bistros = bistroService.getMiddle(wide);
        //then
        assertEquals(count, bistros.stream().count());
    }

    //6-2
    @Test
    public void 음식점대중분류별조회(){
        //given 조회하려는 대분류, 중분류가 주어졌을 경우
        String wide = "서울특별시";
        String middle = "강남구";

        //when 주어진 대, 중분류 에 해당하는 식당들 조회
        Object listCount = em.createQuery("select COUNT(b.name)" +
                        " from Bistro b where b.wide = :wide and b.middle = :middle")
                .setParameter("wide",wide)
                .setParameter("middle", middle)
                .getSingleResult();
        List<Bistro> categoryList = bistroService.getCategoryList(wide,middle);

        Object categoryCount = em.createQuery("select COUNT(DISTINCT(b.category))" +
                        " from Bistro b where b.wide = :wide and b.middle = :middle")
                .setParameter("wide",wide)
                .setParameter("middle", middle)
                .getSingleResult();
        List<Bistro> categoryGroup = bistroService.getCategories(wide,middle);


        //then
        assertEquals(listCount, categoryList.stream().count());
        assertEquals(categoryCount, categoryGroup.stream().count());

    }

}
