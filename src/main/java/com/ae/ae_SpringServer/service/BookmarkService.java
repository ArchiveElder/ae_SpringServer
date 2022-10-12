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

    public List<BistroV2> findBookmark(Long userId) {
        return bookmarkRepository.findBookmark(userId);
    }

    /*
    *
    * @Transactional
	public int delete(long id) {
		Optional<User> oUser = userRepository.findById(id);
		if(oUser.isPresent()) {
			userRepository.delete(oUser.get());
			return 1;
		}
		return 0;
	}*/
    @Transactional
    public Long deleteBookmark(Long userId, Long bistroId) {
        Long findBmId = bookmarkRepository.findBookmarkById(userId, bistroId);
        if(findBmId != null){
            Long deleteId = bookmarkRepository.deleteBoomark(findBmId);
            return deleteId;
        }
        return 0L;
    }

    public Long findBookmarkId(Long userId, Long bistroId) {
        return bookmarkRepository.findBookmarkById(userId, bistroId);
    }
}
