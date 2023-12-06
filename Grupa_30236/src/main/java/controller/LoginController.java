package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import view.AdminView;
import view.CustomerView;
import view.EmployeeView;
import view.LoginView;

import java.util.EventListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService){
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.bookService = bookService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfully!");
                LoginViewToCustomerView(loginNotification);
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successfully!");
            }
        }

    }

    public void LoginViewToCustomerView(Notification<User> loginNotification) {
        loginView.closeLoginView();

        if (loginNotification.getResult().getRoles().get(0).getRole().equals("customer")) {
            Stage customerViewStage = new Stage();
            CustomerView customerView = new CustomerView(customerViewStage);
            CustomerController customerController = new CustomerController(customerView, bookService);
            customerViewStage.show();
        }
        if (loginNotification.getResult().getRoles().get(0).getRole().equals("employee")) {
            Stage employeeViewStage = new Stage();
            EmployeeView employeeView = new EmployeeView(employeeViewStage);
            EmployeeController employeeController = new EmployeeController(employeeView, bookService);
            employeeViewStage.show();

        }
        if (loginNotification.getResult().getRoles().get(0).getRole().equals("administrator")) {
            Stage adminViewStage = new Stage();
            AdminView adminView = new AdminView(adminViewStage);
            AdminController adminController = new AdminController(adminView,authenticationService);
            adminViewStage.show();

        }
    }


}