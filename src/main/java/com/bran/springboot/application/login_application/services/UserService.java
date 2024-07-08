package com.bran.springboot.application.login_application.services;
import com.bran.springboot.application.login_application.entities.User;

import jakarta.validation.Valid;

public interface UserService { 
public Iterable<User> getAllUsers();

public User createUser(User user) throws Exception; 
}
