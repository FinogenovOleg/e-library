package com.library.elibrary.Entyties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank
    @Column(unique = true)
    String fio;
    @NotNull
    @Min(1900)
    int birthDayYear;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    List<Book> books = new ArrayList<>();

    public Person() {
    }

    public Person(String fio, int birthDayYear) {
        this.fio = fio;
        this.birthDayYear = birthDayYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getBirthDayYear() {
        return birthDayYear;
    }

    public void setBirthDayYear(int birthDayYear) {
        this.birthDayYear = birthDayYear;
    }

    public List<Book> getBooksList() {
        return books;
    }

    public void setBooksList(List<Book> list) {
        this.books = list;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", birthDayYear=" + birthDayYear +
                '}';
    }
}
