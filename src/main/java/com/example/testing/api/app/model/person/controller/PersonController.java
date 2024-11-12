package com.example.testing.api.app.model.person.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testing.api.app.model.person.entitie.Person;
import com.example.testing.api.app.model.person.request.PersonRequest;
import com.example.testing.api.app.model.person.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;

  @PostMapping
  public ResponseEntity<?> save(@RequestBody PersonRequest req) {
    try {
      return ResponseEntity.ok(personService.save(req));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Person> getPerson(@PathVariable Long id) {
    return new ResponseEntity<>(personService.getPerson(id), null, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<List<Person>> getPeople() {
    return new ResponseEntity<>(personService.getPeople(), null, HttpStatus.OK);
  }

}
