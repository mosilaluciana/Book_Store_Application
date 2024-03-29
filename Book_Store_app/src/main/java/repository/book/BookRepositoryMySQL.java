package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";

        List<Book> books = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    /**
     * How to reproduce a sql injection attack on insert statement
     * <p>
     * <p>
     * 1) Uncomment the lines below and comment out the PreparedStatement part
     * 2) For the Insert Statement DROP TABLE SQL Injection attack to succeed we will need multi query support to be added to our connection
     * Add to JDBConnectionWrapper the following flag after the DB_URL + schema concatenation: + "?allowMultiQueries=true"
     * 3) book.setAuthor("', '', null); DROP TABLE book; -- "); // this will delete the table book
     * 3*) book.setAuthor("', '', null); SET FOREIGN_KEY_CHECKS = 0; SET GROUP_CONCAT_MAX_LEN=32768; SET @tables = NULL; SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()); SELECT IFNULL(@tables,'dummy') INTO @tables; SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables); PREPARE stmt FROM @tables; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET FOREIGN_KEY_CHECKS = 1; --"); // this will delete all tables. You are not required to know the table name anymore.
     * 4) Run the program. You will get an exception on findAll() method because the test_library.book table does not exist anymore
     */


    // ALWAYS use PreparedStatement when USER INPUT DATA is present
    // DON'T CONCATENATE Strings!
    @Override
    public boolean save(Book book) {
        String sql = "INSERT INTO book VALUES(null, ?, ?, ?, ?, ?);";

        // String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() +"\', \'"+ book.getTitle()+"\', null \'" + book.getStock()+ "\', \'" + book.getPrice() +"\');";


        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(newSql);
//            return true;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setLong(4, book.getStock());
            preparedStatement.setLong(5,book.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setStock(resultSet.getLong("stock"))
                .setPrice(resultSet.getLong("price"))
                .build();
    }
    //@Override
    public boolean updateStockById(Long id) {
        String sql = "UPDATE book SET stock = stock - 1 WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateById(Long id, String author, String title, Long price, Long stock, LocalDate publishedDate) {

        String sql = "UPDATE book SET author = ?, title = ?, price = ?, stock = ?, publishedDate = ? WHERE id = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author);
            preparedStatement.setString(2, title);
            preparedStatement.setLong(3, price);
            preparedStatement.setLong(4, stock);
            preparedStatement.setObject(5, publishedDate);
            preparedStatement.setLong(6, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateAuthor(Long id, String author) {

        String sql = "UPDATE book SET author = ? WHERE id = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateTitle(Long id, String Title) {

        String sql = "UPDATE book SET title = ? WHERE id = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Title);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatePrice(Long id, Long price) {

        String sql = "UPDATE book SET price = ? WHERE id = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, price);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateStock(Long id, Long stock) {

        String sql = "UPDATE book SET stock = ? WHERE id = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, stock);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateDate(Long id, LocalDate date) {

        String sql = "UPDATE book SET publishedDate = ? WHERE id = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, date);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            return (rowsUpdated != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeById(Long deleteId) {
        String sql = "DELETE FROM book WHERE id = ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, deleteId);
            statement.executeUpdate(); // Use executeUpdate without passing the SQL query

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
