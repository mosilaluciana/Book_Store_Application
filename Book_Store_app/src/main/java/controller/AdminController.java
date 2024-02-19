package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import model.Book;
import model.User;
import model.builder.BookBuilder;

import model.builder.UserBuilder;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.AdminView;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

        private final AdminView adminView;
        private final AuthenticationService authenticationService;


        public AdminController(AdminView adminView, AuthenticationService authenticationService){
            this.adminView = adminView;
            this.authenticationService =authenticationService;

            this.adminView.addCreateButtonListener(new CreateButtonListener());
            this.adminView.addRetrieveButtonListener(new RetrieveButtonListener());
            this.adminView.addUpdateButtonListener(new UpdateButtonListener());
            this.adminView.addDeleteButtonListener(new DeleteButtonListener());
            refreshTable();
            List<User> listUsers = authenticationService.findAll();

            adminView.setData(listUsers);
        }


        public class CreateButtonListener implements EventHandler<ActionEvent> {

            @Override
            public void handle(ActionEvent event) {

                List<User> users;
                users = authenticationService.findAll();

                String username = adminView.getUsername();
                String password = adminView.getPassword();
                System.out.println(username);
                Notification<Boolean> registerNotification = authenticationService.register(username, password);
                int verification = 0;
                for (User element : users) {
                    if (element.getUsername().equals(username)) {
                        adminView.setActionTarget("Username taken! ", Color.RED);
                        verification = 1;
                    }
                }
                if (registerNotification.hasErrors()) {
                    adminView.setActionTarget(registerNotification.getFormattedErrors(), Color.RED);
                } else {
                    if (verification == 0) {
                        adminView.setActionTarget("Register successful!", Color.GREEN);
                    }
                }

                refreshTable();
            }
        }



        private class RetrieveButtonListener implements EventHandler<ActionEvent>{
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String username = adminView.getUsername();
                List<User> users = new ArrayList<>();
                if (authenticationService.existsByUsername(username)!=null) {
                    users.add(authenticationService.existsByUsername(username));
                    adminView.setData(users);
                }
                else{
                    adminView.setActionTarget("User does not exist!", Color.RED);
                }
            }
        }

        private class UpdateButtonListener implements EventHandler<ActionEvent>{

            private String username;
            private String password;

            @Override
            public void handle(javafx.event.ActionEvent event) {
                User userSelected = adminView.getSelectedUser();

                if (userSelected != null) {
                    Long idSelected = userSelected.getId();

                    if (!adminView.getUsername().isEmpty() && !adminView.getPassword().isEmpty()) {
                        username = adminView.getUsername();
                        password = adminView.getPassword();
                        authenticationService.updateEmployee(idSelected,username,password);
                    }

                    refreshTable();

                } else {
                    adminView.setActionTarget("No User selected!", Color.BLACK);
                }
            }
        }

        private class DeleteButtonListener implements EventHandler<ActionEvent> {


            @Override
            public void handle(javafx.event.ActionEvent event) {

                    User userSelected = adminView.getSelectedUser();
                if (userSelected != null) {
                    Long idUser = userSelected.getId();
                    authenticationService.removeById(idUser);
                    refreshTable();
                    adminView.setActionTarget("User removed!", Color.GREEN);
                } else {
                    adminView.setActionTarget("No User selected!", Color.BLACK);
                }

            }
        }
            public void refreshTable() {
                List<User> listUsers = authenticationService.findAll();
                adminView.setData(listUsers);
            }


}
