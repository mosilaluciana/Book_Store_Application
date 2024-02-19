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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import model.Book;
import java.time.LocalDate;
import java.util.List;

public class EmployeeView {

        private TableView<Book> tableView;
        private Button sellButton;
        private Text actionTarget;
        private ObservableList<Book> booksData;

        private Button createButton;
        private Button retrieveButton; //Retrieve is for searching and reading data
        private Button updateButton;
        private Button deleteButton;

        private Label idLabel;
        private Label authorLabel;
        private Label titleLabel;
        private Label priceLabel;
        private Label stockLabel;
        private Label publishedDateLabel;

        private TextField idField;
        private TextField authorField;
        private TextField titleField;
        private TextField priceField;
        private TextField stockField;
        private TextField publishedDateField;


    public EmployeeView(Stage secondStage) {
            secondStage.setTitle("Employee view");

            GridPane gridPane = new GridPane();
            initializeGridPane(gridPane);

            Scene scene = new Scene(gridPane, 720, 480);
            secondStage.setScene(scene);

            tableView = new TableView<>();
            initializeFields(gridPane);

            idLabel = new Label("Id: ");
            idField = new TextField();
            idField.setEditable(true);

            authorLabel = new Label("Author: ");
            authorField = new TextField();
            authorField.setEditable(true);

            titleLabel = new Label("Title: ");
            titleField = new TextField();
            titleField.setEditable(true);

            priceLabel = new Label("Price: ");
            priceField = new TextField();
            priceField.setEditable(true);

            stockLabel = new Label("Stock: ");
            stockField = new TextField();
            stockField.setEditable(true);

            publishedDateLabel = new Label("Published Date: ");
            publishedDateField = new TextField();
            publishedDateField.setEditable(true);

            VBox labelsVBox = new VBox();
            labelsVBox.setAlignment(Pos.CENTER_LEFT);
            labelsVBox.getChildren().addAll(authorLabel,authorField,titleLabel,titleField,priceLabel,priceField, stockLabel,stockField,publishedDateLabel, publishedDateField, idLabel, idField);
            gridPane.add(labelsVBox,1,1);

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

            tableView.getColumns().addAll(idColumn, authorColumn, titleColum, priceColumn, stockColumn, publishedDateColumn);


            sellButton = new Button("Sell Book");
            HBox sellButtonHBox = new HBox(10);
            sellButtonHBox.setAlignment(Pos.BOTTOM_CENTER);



            createButton = new Button("Create Book");
            retrieveButton = new Button("Retrieve Book");
            updateButton = new Button("Update Book");
            deleteButton = new Button("Delete Book");

            sellButtonHBox.getChildren().addAll(sellButton,createButton,retrieveButton,updateButton,deleteButton);
            gridPane.add(sellButtonHBox, 1, 4);
        }

        public void setData(List<Book> bookList){
            booksData = FXCollections.observableArrayList(bookList);
            tableView.setItems(booksData);
        }


        public void addCreateButtonListener(EventHandler<ActionEvent> createButtonActionListener) {
            createButton.setOnAction(createButtonActionListener);
        }

        public void addRetrieveButtonListener(EventHandler<ActionEvent> retrieveButtonActionListener) {
            retrieveButton.setOnAction(retrieveButtonActionListener);
        }

        public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonActionListener) {
            updateButton.setOnAction(updateButtonActionListener);
        }

        public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonActionListener) {
            deleteButton.setOnAction(deleteButtonActionListener);
        }
        public void addSellButtonListener(EventHandler<ActionEvent> sellButtonActionListener) {
            sellButton.setOnAction(sellButtonActionListener);
        }


        public Book getSelectedBook() {
            return tableView.getSelectionModel().getSelectedItem();
        }


        public void setActionTarget(String text, Color color){
            this.actionTarget.setText(text);
            this.actionTarget.setFill(color);
        }

        public String getId(){return idField.getText();}

        public String getAuthor(){
            return authorField.getText();
        }

        public String getTitle(){
            return titleField.getText();
        }

        public String getPrice(){
          return priceField.getText();
        }

        public String getStock(){
            return stockField.getText();
        }

        public String getPublishedDate(){

            return publishedDateField.getText();
        }

}



