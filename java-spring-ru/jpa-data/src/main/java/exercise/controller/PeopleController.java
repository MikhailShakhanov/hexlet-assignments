package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Person;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> showPersons() {
        return personRepository.findAll().stream().toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person savePerson(@RequestBody Person person) {
        personRepository.save(person);
        return person;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }

}
