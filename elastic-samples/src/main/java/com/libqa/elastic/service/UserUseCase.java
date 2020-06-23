package com.libqa.elastic.service;

import com.libqa.elastic.domain.document.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author edell.lee
 * @version 2020-06-19 12:39
 * @implNote
 */
@Service
public interface UserUseCase {

    Mono<Void> index(String index, String type, String userName, String message);

    Flux<User> matchAll(String index);

    Mono<List<User>> matchAllSync(String index);

    Mono<User> getUser(String index, String type, String id);
}
