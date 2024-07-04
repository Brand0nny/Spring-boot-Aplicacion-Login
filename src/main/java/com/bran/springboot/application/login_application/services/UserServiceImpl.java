package com.bran.springboot.application.login_application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bran.springboot.application.login_application.entities.User;
import com.bran.springboot.application.login_application.repositories.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Iterable<User> getAllUsers() {

    return userRepository.findAll();
    }

}
