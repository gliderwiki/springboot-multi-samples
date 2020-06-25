package com.libqa.redis.service;

import com.libqa.redis.entity.Person;
import com.libqa.redis.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author edell.lee
 * @version 2020-06-24 13:43
 * @implNote
 */
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public Person create(String firstName, String lastName, int age) {
        return personRepository.save(new Person(firstName, lastName, age));
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    public Person update(String firstName, String lastName, int age) {
        Person person = personRepository.findByFirstName(firstName);

        person.setLastName(lastName);
        person.setAge(age);
        return personRepository.save(person);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

}
