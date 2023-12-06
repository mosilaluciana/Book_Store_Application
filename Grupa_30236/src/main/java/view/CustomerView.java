package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;

import java.util.List;

public class CustomerView {

    private TableView<Book> tableView;
    private Button buyButton;
    private Text actionTarget;
    private ObservableList<Book> booksData;


    public CustomerView(Stage secondStage) {
        secondStage.setTitle("Customer view");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        secondStage.setScene(scene);

        tableView = new TableView<>();
        initializeFields(gridPane);

        gridPane.add(tableView, 1, 6);

        initializeSceneTitle(gridPane);

         secondStage.show();

    }
    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane){
        Text sceneTitle = new Text("Book Table");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        this.actionTarget = new Text();
        actionTarget.setFont(Font.font("Tahome", FontWeight.NORMAL, 15));

        gridPane.add(actionTarget, 1, 3, 2, 1);

    }


    private void initializeFields(GridPane gridPane) {
        TableColumn<Book,Long> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> titleColum = new TableColumn<>("Title");
        titleColum.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, Long> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, Long> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Book, String> publishedDateColumn = new TableColumn<>("Published Date");
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

        buyButton = new Button("Buy Book");
        HBox buyButtonHBox = new HBox(10);
        buyButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buyButtonHBox.getChildren().add(buyButton);
        gridPane.add(buyButtonHBox, 1, 4);

        tableView.getColumns().addAll(idColumn, authorColumn, titleColum, priceColumn, stockColumn, publishedDateColumn);

    }

    public void setData(List<Book> bookList){
        booksData = FXCollections.observableArrayList(bookList);
        tableView.setItems(booksData);
    }

    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonActionListener) {
        buyButton.setOnAction(buyButtonActionListener);
    }

    public Book getSelectedBook() {
        return tableView.getSelectionModel().getSelectedItem();
    }


    public void setActionTarget(String text, Color color){
        this.actionTarget.setText(text);
        this.actionTarget.setFill(color);
    }
}

