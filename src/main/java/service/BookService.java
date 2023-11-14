package service;

import model.Book;

import java.util.List;

public interface BookService<T extends Book> {

    List<T> findAll();

    T findById(Long id);

    boolean save(T entity);

    int getAgeOfBook(Long id);
}