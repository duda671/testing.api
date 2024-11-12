package com.example.testing.api.app.model.person.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.testing.api.app.model.person.entitie.Person;
import com.example.testing.api.app.model.person.repository.PersonRepo;
import com.example.testing.api.app.model.person.request.PersonRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepo personRepo;

  @Transactional
  public Person save(PersonRequest req) {
    Person person = new Person();
    person.setName(req.getName());
    person.setAge(req.getAge());
    person.setEmail(req.getEmail());

    return personRepo.save(person);
  }

  @Transactional
  public void deletePerson(Long id) {
    personRepo.deleteById(id);
  }

  @Cacheable(value = "persons", key = "#id")
  public List<Person> getPeople() {
    return personRepo.findAll();
  }

  public Person getPerson(Long id) {
    Optional<Person> person = personRepo.findById(id);
    if (person.isPresent()) {
      return person.get();
    } else {
      throw new RuntimeException("Person not found");
    }
  }
}
