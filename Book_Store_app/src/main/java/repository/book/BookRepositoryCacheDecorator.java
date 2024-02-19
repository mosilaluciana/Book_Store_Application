package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{
    private Cache<Book> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache){
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<Book> findAll() {
        if (cache.hasResult()){
           return cache.load();
        }

        List<Book> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {

        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Book book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public boolean updateStockById (Long id){
        cache.invalidateCache();
        return decoratedRepository.updateStockById(id);
    }

    public boolean updateById(Long id, String author, String title, Long price, Long stock, LocalDate publishedDate){
        cache.invalidateCache();
        return decoratedRepository.updateById(id, author,title, price, stock, publishedDate);
    }

    public boolean updateAuthor(Long id, String author){
        cache.invalidateCache();
        return decoratedRepository.updateAuthor(id, author);
    }
    public boolean updateTitle(Long id, String title){
        cache.invalidateCache();
        return decoratedRepository.updateTitle(id, title);
    }
    public boolean updatePrice(Long id, Long price){
        cache.invalidateCache();
        return decoratedRepository.updatePrice(id, price);
    }
    public boolean updateStock(Long id, Long stock){
        cache.invalidateCache();
        return decoratedRepository.updateStock(id, stock);
    }

    public boolean updateDate(Long id, LocalDate publishedDate){
        cache.invalidateCache();
        return decoratedRepository.updateDate(id, publishedDate);
    }

    @Override
    public boolean removeById(Long deleteId) {
        cache.invalidateCache();
        return decoratedRepository.removeById(deleteId);
    }
}
