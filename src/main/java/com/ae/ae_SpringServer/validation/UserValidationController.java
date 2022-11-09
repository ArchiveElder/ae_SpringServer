package com.ae.ae_SpringServer.validation;

import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.exception.AeException;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.ae.ae_SpringServer.exception.CodeAndMessage.EMPTY_USER;

@Controller
@RequiredArgsConstructor
public class UserValidationController {
    private final UserService userService;
    public User validateuser(Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user == Optional.<User>empty()) throw new AeException(EMPTY_USER);
        else return user.get();

    }
}