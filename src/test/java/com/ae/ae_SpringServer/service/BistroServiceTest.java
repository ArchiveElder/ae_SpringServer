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

}
