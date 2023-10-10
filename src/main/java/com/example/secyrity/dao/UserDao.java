package com.example.secyrity.dao;

import com.example.secyrity.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void deleteUserById(int id);

    void updateUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getListOfUsers();
}