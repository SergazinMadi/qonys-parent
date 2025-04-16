package serg.madi.userservice.service;

import serg.madi.userservice.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUser(Long id);
}
