package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepositoryMySQL;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

import java.util.EventListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookRepositoryMySQL bookRepository;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookRepositoryMySQL bookRepository) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.bookRepository = bookRepository;

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
                LoginViewToCustomerView();
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

    public void LoginViewToCustomerView(){
        loginView.closeLoginView();

        Stage customerViewStage = new Stage();
        CustomerView customerView = new CustomerView(customerViewStage);
        CustomerController customerController = new CustomerController(customerView, this.bookRepository);
        customerViewStage.show();

    }


}