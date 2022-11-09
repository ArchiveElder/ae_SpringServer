package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.repository.BookmarkRepository;
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
public class BookmarkServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    BistroService bistroService;
    @Autowired
    BookmarkRepository bookmarkRepository;

    @Test
    public void 즐겨찾기등록() {
        // given
        User user = new User();
        user.setName("홍길동");
        user.setGender(0);
        user.setAge(23);
        user.setHeight("170");
        user.setWeight("70");
        user.setIcon(1);
        user.setActivity(40);
        em.persist(user);

        List<Bistro> allBistro = bistroService.getBistro();
        List<BistroV2> allBistroV2 = bistroService.getBistroV2();

        // when
        Bookmark bookmark = Bookmark.createBookmark(user, allBistro.get(0), allBistroV2.get(0).getId());
        Long id = bookmarkService.create(bookmark);

        // then
        List<Bistro> bm = bookmarkService.findBookmark(id);
        if(bm.size() != 0) {
            assertEquals(bm.get(0).getName(), "채근담 대치점");
        }
    }

    @Test
    public void 즐겨찾기조회() {
        // given
        User user = new User();
        user.setName("홍길동");
        user.setGender(0);
        user.setAge(23);
        user.setHeight("170");
        user.setWeight("70");
        user.setIcon(1);
        user.setActivity(40);
        em.persist(user);

        List<Bistro> allBistro = bistroService.getBistro();
        List<BistroV2> allBistroV2 = bistroService.getBistroV2();

        // when
        Bookmark bookmark = Bookmark.createBookmark(user, allBistro.get(0), allBistroV2.get(0).getId());
        Long id = bookmarkService.create(bookmark);
        List<Bistro> bm = bookmarkService.findBookmark(id);

        // then
        if(bm.size() != 0) {
            assertEquals(bm.get(0).getName(), "채근담 대치점");
        }
    }

}
