package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    boolean save(Book book);

    void removeAll();

   boolean updateStockById(Long id);
   boolean updateById(Long id, String author, String title, Long price, Long stock, LocalDate publishedDate);
   boolean updateAuthor(Long id, String author);
   boolean updateTitle(Long id, String title);
   boolean updatePrice(Long id, Long price);
   boolean updateStock(Long id, Long stock);
   boolean updateDate(Long id, LocalDate publishedDate);
   boolean removeById(Long deleteId);
}
