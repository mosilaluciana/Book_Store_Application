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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class CustomerController {

    private final CustomerView customerView;
    private final BookService bookService;


    public CustomerController(CustomerView customerView, BookService bookService){
            this.customerView = customerView;
            this.bookService =bookService;

            this.customerView.addBuyButtonListener(new BuyButtonListener());

            List<Book> listBooks = bookService.findAll();

            customerView.setData(listBooks);
    }


    private class BuyButtonListener implements EventHandler<ActionEvent> {

        private String author;
        private String title;
        private List<Book> booksSold = new ArrayList<>();
        private Book selectedBook;
        private Integer price = 0;



        public void generatePDFReport(List<Book> booksSold) {
            try {
                PdfDocument pdfDocument = new PdfDocument(new PdfWriter("BooksSoldReport.pdf"));
                Document document = new Document(pdfDocument);

                Paragraph title = new Paragraph("Books Sold Report")
                        .setBold()
                        .setFontSize(18);
                document.add(title);

                for (Book book : booksSold) {
                    Paragraph bookInfo = new Paragraph()
                            .add("Title: " + book.getTitle())
                            .add("\nAuthor: " + book.getAuthor())
                            .add("\nPrice: $" + book.getPrice())
                            .add("\n---------------");
                    document.add(bookInfo);
                }

                document.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }




        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book bookSelected = customerView.getSelectedBook();

            if (bookSelected != null) {
                Long idSelected = bookSelected.getId();

                if (bookSelected.getStock() > 0) {
                    customerView.setActionTarget("Book Sold", Color.GREEN);
                    bookService.updateStockById(idSelected);
                    booksSold.add(bookSelected);
                    List<Book> listBooks =bookService.findAll();
                    customerView.setData(listBooks);
                } else {
                    customerView.setActionTarget("Sold Out", Color.RED);
                }
            } else {
                customerView.setActionTarget("No book selected!", Color.BLACK);
            }
            generatePDFReport(booksSold);
        }

    }

}