package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Long create(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    public List<BistroV2> findBookmarkV2(Long userId) {
        return bookmarkRepository.findBookmarkV2(userId);
    }

    @Transactional
    public Long deleteBookmark(Long userId, Long bistroId) {
        Long findBmId = bookmarkRepository.findBookmarkById(userId, bistroId);
        if(findBmId != null){
            Long deleteId = bookmarkRepository.deleteBoomark(findBmId);
            return deleteId;
        }
        return 0L;
    }

    public Long findBookmarkIdV2(Long userId, Long bistroId) {
        return bookmarkRepository.findBookmarkByIdV2(userId, bistroId);
    }

    /*
    * version 1 사용자를 위한 코드
    * */
    public List<Bistro> findBookmark(Long userId) {
        return bookmarkRepository.findBookmark(userId);
    }
    public Long findBookmarkId(Long userId, Long bistroId) {
        return bookmarkRepository.findBookmarkById(userId, bistroId);
    }
}
