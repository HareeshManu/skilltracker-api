package com.resource.skillstracker.service;

import com.resource.skillstracker.model.Person;
import com.resource.skillstracker.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void testSavePerson() {
        Person person = new Person();
        person.setName("John Doe");

        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person savedPerson = personService.save(person);

        assertNotNull(savedPerson);
        assertEquals("John Doe", savedPerson.getName());
        verify(personRepository, times(1)).save(person);
    }
   
}
