package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

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

    public List<Bistro> findBookmark(Long id) {
        return bookmarkRepository.findBookmark(id);
    }


}
