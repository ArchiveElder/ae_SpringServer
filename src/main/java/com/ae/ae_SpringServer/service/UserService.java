package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /*
    int updateBadge(Long id) {
        return userRepository.updateOne(id);
    }
     */

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }
    void updateSome(Long id, Record record) { userRepository.updateSome(id, record); }

    public int findBadge(Long id) {
        return userRepository.findBadge(id);
    }
}
