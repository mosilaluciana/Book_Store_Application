package service.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    boolean save(Book book);

    int getAgeOfBook(Long id);

    boolean updateStockById(Long id);
    boolean updateById(Long id, String author, String title, Long price, Long stock, LocalDate publishedDate);

    boolean updateAuthor(Long id, String author);
    boolean updateTitle(Long id, String title);
    boolean updatePrice(Long id, Long price);
    boolean updateStock(Long id, Long stock);
    boolean updateDate(Long id, LocalDate publishedDate);

    boolean removeById(Long deleteId);
}
