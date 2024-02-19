package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Book;
import model.builder.BookBuilder;
import org.w3c.dom.events.Event;
import service.book.BookService;
import view.CustomerView;
import view.EmployeeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

        private final EmployeeView employeeView;
        private final BookService bookService;


        public EmployeeController(EmployeeView employeeView, BookService bookService){
            this.employeeView = employeeView;
            this.bookService =bookService;

            this.employeeView.addCreateButtonListener(new CreateButtonListener());
            this.employeeView.addRetrieveButtonListener(new RetrieveButtonListener());
            this.employeeView.addUpdateButtonListener(new UpdateButtonListener());
            this.employeeView.addDeleteButtonListener(new DeleteButtonListener());

            this.employeeView.addSellButtonListener(new SellButtonListener());
            List<Book> listBooks = bookService.findAll();

            employeeView.setData(listBooks);
        }


        private class CreateButtonListener implements EventHandler<ActionEvent>{

            private String author;
            private String title;
            private Long stock;
            private Long price;
            private LocalDate publishedDate;

            @Override
            public void handle(javafx.event.ActionEvent event) {

                author = employeeView.getAuthor();
                title = employeeView.getTitle();
                stock = Long.parseLong(employeeView.getStock());
                price = Long.parseLong(employeeView.getPrice());
                publishedDate = LocalDate.parse(employeeView.getPublishedDate());

                        Book book = new BookBuilder()
                                .setTitle(title)
                                .setAuthor(author)
                                .setPublishedDate(publishedDate)
                                .setStock(stock)
                                .setPrice(price)
                                .build();
                        bookService.save(book);
                    }
        }


         private class RetrieveButtonListener implements EventHandler<ActionEvent>{
             @Override
             public void handle(javafx.event.ActionEvent event) {
                 Long id = Long.parseLong(employeeView.getId());
                 List<Book> book = new ArrayList<>();
                 if (bookService.findById(id) != null) {
                     book.add(bookService.findById(id));
                     employeeView.setData(book);
                 }
                 else{
                      employeeView.setActionTarget("Book does not exist!", Color.RED);
                 }
             }
         }

         private class UpdateButtonListener implements EventHandler<ActionEvent>{

            private String author;
            private String title;
            private Long stock;
            private Long price;
            private LocalDate publishedDate;

            @Override
            public void handle(javafx.event.ActionEvent event) {
                Book bookSelected = employeeView.getSelectedBook();

                if (bookSelected != null) {
                    Long idSelected = bookSelected.getId();

                    if (!employeeView.getAuthor().isEmpty()) {
                        author = employeeView.getAuthor();
                        bookService.updateAuthor(idSelected,author);
                    }

                    if (!employeeView.getTitle().isEmpty()) {
                        title = employeeView.getTitle();
                        bookService.updateTitle(idSelected, title);
                    }

                    if (!employeeView.getStock().isEmpty()){
                        stock = Long.parseLong(employeeView.getStock());
                       bookService.updateStock(idSelected,stock);
                    }

                    if (!employeeView.getPrice().isEmpty()){
                        price = Long.parseLong(employeeView.getPrice());
                        bookService.updatePrice(idSelected, price);
                    }

                    if(!employeeView.getPublishedDate().isEmpty()) {
                        publishedDate = LocalDate.parse(employeeView.getPublishedDate());
                        bookService.updateDate(idSelected,publishedDate);
                    }

                    refreshTable();

                } else {
                    employeeView.setActionTarget("No book selected!", Color.BLACK);
                }
            }
        }

        private class DeleteButtonListener implements EventHandler<ActionEvent>{

            Book bookSelected = employeeView.getSelectedBook();

            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (bookSelected != null) {
                    bookSelected = employeeView.getSelectedBook();
                    Long idSelectedBook = bookSelected.getId();
                    bookService.removeById(idSelectedBook);
                    refreshTable();
                    employeeView.setActionTarget("Book removed!", Color.GREEN);
                }
                else employeeView.setActionTarget("No book selected!", Color.BLACK);

            }
        }



        private class SellButtonListener implements EventHandler<ActionEvent> {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                Book bookSelected = employeeView.getSelectedBook();

                if (bookSelected != null) {
                    Long idSelected = bookSelected.getId();

                    if (bookSelected.getStock() > 0) {
                        employeeView.setActionTarget("Book Sold", Color.GREEN);
                        bookService.updateStockById(idSelected);
                        List<Book> listBooks =bookService.findAll();
                        employeeView.setData(listBooks);
                    } else {
                        employeeView.setActionTarget("Sold Out", Color.RED);
                    }
                } else {
                    employeeView.setActionTarget("No book selected!", Color.BLACK);
                }
            }

        }

    public void refreshTable(){
        List<Book> listBooks =bookService.findAll();
        employeeView.setData(listBooks);
    }

}


