package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock<T> implements BookRepository<T>{

    private List<T> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<T> findAll() {
        return books;
    }

    @Override
    public Optional<T> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getClass().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(T book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}