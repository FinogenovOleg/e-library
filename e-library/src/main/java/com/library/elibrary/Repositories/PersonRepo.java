package com.library.elibrary.Repositories;

import com.library.elibrary.Entyties.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person,Long> {
}
