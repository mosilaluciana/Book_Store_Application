package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {

    private List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean updateStockById(Long id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                book.setStock(book.getStock() - 1);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean updateById(Long id, String author, String title, Long price, Long stock, LocalDate publishedDate){
        return false;
    }
    public boolean updateAuthor(Long id, String author){
        return false;
    }
    public boolean updateTitle(Long id, String title){
        return false;
    }
    public boolean updatePrice(Long id, Long price){
        return false;
    }
    public boolean updateStock(Long id, Long stock){return false;}
    public boolean updateDate(Long id, LocalDate publishedDate){
        return false;
    }
    public boolean removeById(Long deleteId){return false;}

}
