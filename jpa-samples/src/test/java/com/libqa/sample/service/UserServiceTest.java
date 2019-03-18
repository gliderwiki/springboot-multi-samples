package com.libqa.sample.service;

import com.libqa.sample.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void saveUserRegisterTest() {
        User user = new User("edell.lee", "edell.lee@hanmail.net");

        User registered = userService.register(user);

        assertThat(registered.getCreatedAt()).isNotNull();
    }
}
