package com.libqa.redis.controller;

import com.libqa.redis.entity.User;
import com.libqa.redis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author edell.lee
 * @version 2020-06-23 17:15
 * @implNote
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/add/{id}/{name}")
    public User add(@PathVariable("id") String id, @PathVariable("name") String name) {
        userRepository.save(new User(id, name, 20000L));

        return userRepository.findById(id);
    }

    @GetMapping("/all")
    public Map<String, User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/update/{id}/{name}")
    public User update(@PathVariable("id") String id, @PathVariable("name") String name) {
        userRepository.save(new User(id, name, 200000L));

        return userRepository.findById(id);
    }
}
