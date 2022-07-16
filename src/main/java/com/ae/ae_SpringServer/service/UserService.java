package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.SignupRequestDto;
import com.ae.ae_SpringServer.dto.UserUpdateRequestDto;
import com.ae.ae_SpringServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }
    void updateSome(Long id, Record record) { userRepository.updateSome(id, record); }

    public int findBadge(Long id) {
        return userRepository.findBadge(id);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByKakaoId(String kakao) {
        return userRepository.findByKakao(kakao);
    }

    public void signup(Long id, SignupRequestDto dto) {
        userRepository.signup(id, dto);
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        userRepository.update(id, dto);
    }
}
