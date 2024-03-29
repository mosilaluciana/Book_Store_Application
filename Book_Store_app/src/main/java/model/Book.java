package model;

// Java Bean -

// POJO - Plain Old Java Object


import java.time.LocalDate;
import java.util.Date;

public class Book{

    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;

    private Long stock;

    private Long price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getStock(){ return this.stock;}

    public void setStock(Long stock){ this.stock = stock;}

    public Long getPrice(){return this.price;}

    public void setPrice(Long price){this.price = price;}

    @Override
    public String toString(){
        return String.format("Id: %d | Title: %s | Author: %s | Date: %s | Stock: %d | Price: %d", this.id, this.title, this.author, this.publishedDate, this.stock, this.price);
    }
}
