package com.libqa.elastic.service;

import com.libqa.elastic.domain.document.Coffee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public interface CoffeeUseCase {
    Mono<Void> addDocument(Coffee coffee) throws IOException;

    Flux<Coffee> searchTermQueryByTitle(String title);

    Flux<Coffee> searchMatchPhraseQueryByTitle(String title);

    Flux<Coffee> findAll();
}
