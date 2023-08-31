package com.library.elibrary.Services;

import com.library.elibrary.Entyties.Book;
import com.library.elibrary.Entyties.Person;
import com.library.elibrary.Repositories.BooksRepo;
import com.library.elibrary.Repositories.PersonRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    private BooksRepo booksRepo;
    private PersonRepo personRepo;
    private PersonService personService;
    @Autowired
    public BooksService(BooksRepo booksRepo, PersonRepo personRepo,
                        PersonService personService) {
        this.booksRepo = booksRepo;
        this.personRepo = personRepo;
        this.personService = personService;
    }

    public List<Book> getAll(int page, int itemsPerPage) {
        return booksRepo.findAll(PageRequest.of(page,itemsPerPage, Sort.by("bookTitle"))).
                getContent();
    }

    public void update(Book book) {
        booksRepo.save(book);
    }

    public Book getBook(long id) {
        return booksRepo.findBookById(id);
    }

    public void create(Book book) {
        booksRepo.save(book);
    }

    public void delete(long id) {
        booksRepo.deleteById(id);
    }
    @Transactional
    public void assign(long bookId, Person selectedPerson) {
        booksRepo.findById(bookId).ifPresent(book -> {
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        });
    }

    @Transactional
    public void release(long id) {
        booksRepo.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    public Optional<Book> searchByTitle(String query) {
        Optional<Book> books = booksRepo.findByBookTitleStartingWith(query);
        return books;
    }
}
