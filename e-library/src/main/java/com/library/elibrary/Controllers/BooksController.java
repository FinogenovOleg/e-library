package com.library.elibrary.Controllers;

import com.library.elibrary.Entyties.Book;
import com.library.elibrary.Entyties.Person;
import com.library.elibrary.Services.BooksService;
import com.library.elibrary.Services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/library/books")
public class BooksController {
    private BooksService booksService;
    private PersonService personService;
    @Autowired
    public BooksController(BooksService booksService,
                           PersonService personService) {
        this.booksService = booksService;
        this.personService = personService;
    }

    @GetMapping
    public String getAll(@RequestParam(name = "page", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "items", defaultValue = "2") int itemsPerPage,
                         Model model) {
        model.addAttribute("books", booksService.getAll(pageNumber,itemsPerPage));
        return "book-index.html";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute(name = "book") Book book) {
        return "book-new.html";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute(name = "book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "bool-new.html";

        booksService.create(book);

        return "redirect:/library/books";
    }

    @GetMapping("/{id}")
    public String getBookPage(@PathVariable("id") long id,
                        Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.getBook(id));
        Book book = booksService.getBook(id);
        if (book.getOwner() != null)
            model.addAttribute("owner", book.getOwner());
        else
            model.addAttribute("people", personService.getAll());

        return "book-show.html";
    }

    @GetMapping("/{id}/edite")
    public String edite(@PathVariable("id") long id,
            Model model) {
        model.addAttribute("book", booksService.getBook(id));
        return "book-edite.html";
    }

    @GetMapping("/search")
    public String search() {
        return "searching.html";
    }

    @PostMapping("/book-edite")
    public String edit(Book book) {
        booksService.update(book);
        return "redirect:/library/books";
    }
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") long id) {
        booksService.delete(id);
        return "redirect:/library/books";
    }
    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") long id) {
        booksService.release(id);
        return "redirect:/library/books";
    }
    @PostMapping("/{id}/assign")
    public String setOwner(@PathVariable("id") long bookId,
                           @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(bookId,selectedPerson);
        return "redirect:/library/books";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.searchByTitle(query).stream().toList());
        return "searching.html";
    }
}
