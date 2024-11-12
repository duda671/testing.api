package com.example.testing.api.app.model.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testing.api.app.model.person.entitie.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

}
