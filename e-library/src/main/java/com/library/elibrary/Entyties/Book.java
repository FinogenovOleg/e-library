package com.library.elibrary.Entyties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank
    String bookTitle;
    @NotBlank
    String author;
    @NotNull
    @Min(0)
    int year;

    @ManyToOne
    @JoinColumn(name="person_id")
    Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    @Transient
    private boolean expired;
    public Book() {
    }

    public Book(String bookTitle, String author, int year) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
    public Date getTakenAt() {
        return takenAt;
    }
    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
    public boolean isExpired() {
        return expired;
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}