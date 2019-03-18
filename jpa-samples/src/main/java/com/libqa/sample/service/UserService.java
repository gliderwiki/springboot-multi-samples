package com.libqa.sample.service;

import com.libqa.sample.domain.entity.User;
import com.libqa.sample.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User register(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setLastAccessedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private void setLocalTimeNow(User user) {

    }
}
