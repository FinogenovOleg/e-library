package com.library.elibrary.Controllers;

import com.library.elibrary.Entyties.Person;
import com.library.elibrary.Services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library/people")
public class PeopleController {
    private PersonService peopleService;

    @Autowired
    public PeopleController(PersonService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("people", peopleService.getAll());
        return "person-index.html";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute(name = "person") Person person) {
        return "person-new.html";
    }

    @GetMapping("/{id}/edit")
    public String edite(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id));
        return "person-edite.html";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "person-show.html";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute(name = "person") @Valid Person person,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "person-new.html";

        peopleService.save(person);
        return "redirect:/library/people";
    }
    @PostMapping("/{id}/edite")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "person-edite.html";

        peopleService.save(person);
        return "redirect:/library/people";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        peopleService.delete(id);
        return "redirect:/library/people";
    }
}
