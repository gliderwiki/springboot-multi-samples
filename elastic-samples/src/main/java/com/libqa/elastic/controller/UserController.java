package com.libqa.elastic.controller;

import com.libqa.elastic.domain.document.User;
import com.libqa.elastic.service.UserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * @author edell.lee
 * @version 2020-06-19 12:50
 * @implNote
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    // POST http://localhost:8080/user/index/twitter/_doc?user_name=lee&message=test
    // Content-Type: application/json
    @PostMapping(value = "/index/{index}/{type}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> index(@PathVariable("index") String index,
                            @PathVariable("type") String type,
                            @RequestParam(value = "user_name") String userName,
                            @RequestParam(value = "message") String message) {
        return userUseCase.index(index, type, userName, message)
                .onErrorResume(error -> {
                    log.error("index controller error", error);
                    return Mono.empty();
                });
    }

    @GetMapping(value = "/get/{index}/{type}/{id}")
    public Mono<User> getUser(@PathVariable("index") String index,
                              @PathVariable("type") String type,
                              @PathVariable("id") String id) {
        return userUseCase.getUser(index, type, id)
                .onErrorResume(error -> {
                    User defaultUser = new User();
                    defaultUser.setUser("Default");
                    defaultUser.setPostDate(new Date());
                    defaultUser.setMessage("default message");
                    return Mono.just(defaultUser);
                })
                .defaultIfEmpty(new User());
    }

    // http://localhost:8080/user/match_all/twitter
    @GetMapping("/match_all/{index}")
    public Flux<User> matchAll(@PathVariable("index") String index) {
        log.info("# match app = {}", index);

        return userUseCase.matchAll(index).onErrorResume((Throwable error) -> {
            log.error("err", error);
            User defaultUser = new User();
            defaultUser.setUser("Default");
            defaultUser.setPostDate(new Date());
            defaultUser.setMessage("default message");
            return Flux.just(defaultUser);
        });
    }

    @GetMapping("/sync/match_all/{index}")
    public Mono<List<User>> matchAllSync(@PathVariable("index") String index) {
        return userUseCase.matchAllSync(index);
    }
}
