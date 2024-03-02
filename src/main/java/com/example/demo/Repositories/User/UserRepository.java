package com.example.demo.Repositories.User;

import com.example.demo.Entities.User;

import java.util.List;

public interface UserRepository {

    User addUser(User user);
    List<User> allUsers();
    List<User> pageUsers(Integer page);
    User findUser(Integer id);
    User findByEmailAndPassword(String email, String password_hash);
    User updateUser(User user);
    void updateStatus(Integer id, String status) throws Exception;
    void deleteUser(Integer id);
}
