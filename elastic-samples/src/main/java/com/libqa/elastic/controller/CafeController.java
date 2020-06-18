package com.libqa.elastic.controller;

import com.libqa.elastic.domain.document.Coffee;
import com.libqa.elastic.service.CoffeeUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author edell.lee
 * @version 2020-06-18 13:30
 * @implNote
 */
@RestController
@RequestMapping("/coffees")
public class CafeController {

    private CoffeeUseCase coffeeUseCase;

    public CafeController(CoffeeUseCase coffeeUseCase) {
        this.coffeeUseCase = coffeeUseCase;
    }

    @GetMapping
    public Flux<Coffee> findAll(){

        return coffeeUseCase.findAll()
                .onErrorResume(error -> Flux.empty());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> add(@RequestBody Coffee coffee) throws IOException {
        return coffeeUseCase.addDocument(coffee);
    }

    @GetMapping("/term")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Coffee> findByTitle(@RequestParam(name = "title") String title) {
        return coffeeUseCase.searchTermQueryByTitle(title);
    }

    @GetMapping("/match")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Coffee> matchByTitle(@RequestParam(name="title") String title){

        return coffeeUseCase.searchMatchPhraseQueryByTitle(title);
    }

}
