package com.library.elibrary.Repositories;

import com.library.elibrary.Entyties.Book;
import com.library.elibrary.Entyties.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BooksRepo extends JpaRepository<Book,Long> {
    Book findBookById (Long id);
    Optional<Book> findByBookTitleStartingWith(@Param("title") String title);
}
