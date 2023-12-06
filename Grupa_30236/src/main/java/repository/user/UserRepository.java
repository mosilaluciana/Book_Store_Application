package repository.user;

import model.User;
import model.validator.Notification;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    User existsByUsername(String username);

    boolean removeById(Long deleteId);

    boolean updateEmployee(Long id, String username, String password);

}