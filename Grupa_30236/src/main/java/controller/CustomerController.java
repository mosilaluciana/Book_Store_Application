package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Book;
import model.User;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    private final CustomerView customerView;
    private final BookRepositoryMySQL bookRepository;


    public CustomerController(CustomerView customerView, BookRepositoryMySQL bookRepository) {
            this.customerView = customerView;
            this.bookRepository = bookRepository;
            this.customerView.addBuyButtonListener(new BuyButtonListener());

            List<Book> listBooks = bookRepository.findAll();
            customerView.setData(listBooks);
    }


    private class BuyButtonListener implements EventHandler<ActionEvent> {

        private String author;
        private String title;
        private List<Book> booksSold = new ArrayList<>();
        private Book selectedBook;
        private Integer price = 0;

        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book bookSelected = customerView.getSelectedBook();

            if (bookSelected != null) {
                Long idSelected = bookSelected.getId();

                if (bookSelected.getStock() > 0) {
                    customerView.setActionTarget("Book Sold", Color.GREEN);
                    bookRepository.updateStockById(idSelected);
                    List<Book> listBooks = bookRepository.findAll();
                    customerView.setData(listBooks);
                } else {
                    customerView.setActionTarget("Sold Out", Color.RED);
                }
            } else {
                customerView.setActionTarget("No book selected!", Color.BLACK);
            }
        }

    }

}