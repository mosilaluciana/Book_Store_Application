package service.user;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password);
    List<User> findAll();
    boolean logout(User user);
    User existsByUsername(String email);
    boolean removeById(Long id);
    boolean updateEmployee(Long id, String username, String password);
}