package com.resource.skillstracker.repository;

import com.resource.skillstracker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
