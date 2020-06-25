package com.libqa.redis.controller;

import com.libqa.redis.entity.Person;
import com.libqa.redis.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author edell.lee
 * @version 2020-06-24 14:59
 * @implNote
 */
@Slf4j
@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/create")
    @CachePut(value = "persons", key = "#firstName")
    public Person create(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) {
        log.info("# create = {}", firstName);
        Person p = personService.create(firstName, lastName, age);
        return p;
    }

    @GetMapping("/get")
    @Cacheable(value = "persons", key = "#firstName")
    public Person getPerson(@RequestParam String firstName) {
        log.info("# getPerson = {}", firstName);
        return personService.findByFirstName(firstName);
    }

    @GetMapping("/getAll")
    @Cacheable(value = "persons")
    public List<Person> getAll() {
        log.info("# getAll");
        return personService.getAll();
    }

    @GetMapping("/update")
    @CachePut(value = "persons", key = "#firstName")
    public Person update(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) {
        log.info("# update = {}", firstName);
        Person p = personService.update(firstName, lastName, age);
        return p;
    }

    @GetMapping("/deleteAll")
    @CacheEvict(value = "persons", allEntries = true)
    public void deleteAll() {
        log.info("# deleteAll");
        personService.deleteAll();
    }

}

