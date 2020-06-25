package com.libqa.redis.repository;

import com.libqa.redis.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByFirstName(String firstName);

    List<Person> findByAge(int age);
}
