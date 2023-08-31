package com.library.elibrary.Services;

import com.library.elibrary.Entyties.Book;
import com.library.elibrary.Entyties.Person;
import com.library.elibrary.Repositories.BooksRepo;
import com.library.elibrary.Repositories.PersonRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepo personRepo;
    private BooksRepo booksRepo;

    @Autowired
    public PersonService(PersonRepo personRepo,BooksRepo booksRepo) {
        this.personRepo = personRepo;
        this.booksRepo = booksRepo;
    }

    public List<Person> getAll() {
        return personRepo.findAll();
    }

    public Person getPerson(long id) {
        return personRepo.findById(id).get();
    }

    public List<Book> getBooksByPersonId(long id) {
        Optional<Person> person = personRepo.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooksList());
            // Мы внизу итерируемся по книгам, поэтому они точно будут загружены, но на всякий случай
            // не мешает всегда вызывать Hibernate.initialize()
            // (на случай, например, если код в дальнейшем поменяется и итерация по книгам удалится)

            // Проверка просроченности книг
            person.get().getBooksList().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                // 864000000 милисекунд = 10 суток
                if (diffInMillies > 864000000)
                    book.setExpired(true); // книга просрочена
            });

            return person.get().getBooksList();
        }
        else {
            return Collections.emptyList();
        }
    }

    public void delete(long id) {
        personRepo.deleteById(id);
    }
    public void save(Person person) {
        personRepo.save(person);
    }
}
