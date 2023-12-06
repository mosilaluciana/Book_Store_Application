package service.book;

import model.Book;
import repository.book.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService{

    final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int)ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }

   // @Override
    public boolean updateStockById(Long id){ return bookRepository.updateStockById(id);}

    public boolean updateById(Long id, String author, String title, Long price, Long stock, LocalDate publishedDate){
       return bookRepository.updateById(id, author,title, price, stock, publishedDate);}


    public boolean updateAuthor(Long id, String author){
        return bookRepository.updateAuthor(id, author);
    }
    public boolean updateTitle(Long id, String title){
        return bookRepository.updateTitle(id, title);
    }
    public boolean updatePrice(Long id, Long price){
        return bookRepository.updatePrice(id, price);
    }
    public boolean updateStock(Long id, Long stock){
        return bookRepository.updateStock(id, stock);
    }
    public boolean updateDate(Long id, LocalDate publishedDate){
        return bookRepository.updateDate(id, publishedDate);
    }
    public boolean removeById(Long deleteId){return bookRepository.removeById(deleteId);}
}

