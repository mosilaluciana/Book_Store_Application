package service;

import model.Book;
import repository.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl<T extends Book> implements BookService<T>{

    final BookRepository<T> bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<T> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public T findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(T entity) {
        return bookRepository.save(entity);
    }

    @Override
    public int getAgeOfBook(Long id) {
        T entity = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int)ChronoUnit.YEARS.between(entity.getPublishedDate(), now);
    }
}