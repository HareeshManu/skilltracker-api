package com.resource.skillstracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.resource.skillstracker.dto.PersonDTO;
import com.resource.skillstracker.exception.ResourceNotFoundException;
import com.resource.skillstracker.model.Person;
import com.resource.skillstracker.repository.PersonRepository;
import com.resource.skillstracker.repository.SkillLevelRepository;
import com.resource.skillstracker.repository.SkillRepository;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private SkillLevelRepository skillLevelRepository; 
    
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
    
    
    @Cacheable("people")
    public Page<PersonDTO> getAllPeople(Pageable pageable) {
        return personRepository.findAll(pageable)
            .map(person -> new PersonDTO(person)); // Assuming a constructor in PersonDTO
    }
    
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(personDTO.getName());
            Person updatedPerson = personRepository.save(person);
            return new PersonDTO(updatedPerson);
        } else {
            throw new ResourceNotFoundException("Person not found with id " + id);
        }
    }
}