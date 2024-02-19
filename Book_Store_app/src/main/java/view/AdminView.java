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
import model.User;

import java.util.List;

public class AdminView {

        private TableView<User> tableView;
        private Text actionTarget;
        private ObservableList<User> userData;

        private Button createButton;
        private Button retrieveButton;
        private Button updateButton;
        private Button deleteButton;

        private Label usernameLabel;
        private Label passwordLabel;

        private TextField usernameField;
        private PasswordField passwordField;


        public AdminView(Stage secondStage) {
            secondStage.setTitle("Admin view");

            GridPane gridPane = new GridPane();
            initializeGridPane(gridPane);

            Scene scene = new Scene(gridPane, 720, 480);
            secondStage.setScene(scene);

            tableView = new TableView<>();
            initializeFields(gridPane);

            usernameLabel = new Label("username: ");
            usernameField = new TextField();
            usernameField.setEditable(true);


            passwordLabel = new Label("password: ");
            passwordField = new PasswordField();
            passwordField.setEditable(true);

            VBox labelsVBox = new VBox();
            labelsVBox.setAlignment(Pos.CENTER_LEFT);
            labelsVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
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
            Text sceneTitle = new Text("Employee Table");
            sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
            gridPane.add(sceneTitle, 0, 0, 2, 1);

            this.actionTarget = new Text();
            actionTarget.setFont(Font.font("Tahome", FontWeight.NORMAL, 15));

            gridPane.add(actionTarget, 1, 3, 2, 1);

        }

        private void initializeFields(GridPane gridPane) {
            TableColumn<User,Long> idColumn = new TableColumn<>("Id");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

            TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));


            tableView.getColumns().addAll(idColumn, usernameColumn, passwordColumn);

            HBox buttonsHBox = new HBox(10);
            buttonsHBox.setAlignment(Pos.BOTTOM_CENTER);



            createButton = new Button("Create User");
            retrieveButton = new Button("Retrieve User");
            updateButton = new Button("Update User");
            deleteButton = new Button("Delete User");

            buttonsHBox.getChildren().addAll(createButton,retrieveButton,updateButton,deleteButton);
            gridPane.add(buttonsHBox, 1, 4);
        }

        public void setData(List<User> userList){
            userData = FXCollections.observableArrayList(userList);
            tableView.setItems(userData);
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

        public User getSelectedUser() {
            return tableView.getSelectionModel().getSelectedItem();
        }


        public void setActionTarget(String text, Color color){
            this.actionTarget.setText(text);
            this.actionTarget.setFill(color);
        }


        public String getUsername(){return usernameField.getText();}

        public String getPassword(){
            return passwordField.getText();
        }

    }



