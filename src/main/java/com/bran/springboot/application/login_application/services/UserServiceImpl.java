package com.bran.springboot.application.login_application.services;

import java.util.Optional;

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

    private boolean checkUsername(User user) throws Exception{ 
        Optional<User> userFound = userRepository.findByUsername(user.getUsername());
        if(userFound.isPresent()){
            throw new Exception("no disponible");
        }
return false;
    }
    private boolean checkPasswordMatch(User user) throws Exception {
        if(!user.getPassword().equals(user.getConfirmPassword())){
            throw new Exception("No coinciden");
        }
        return true;
    }

    @Override
    public User createUser(User user) throws Exception {
        if(checkUsername(user)&&checkPasswordMatch(user)){
            user= userRepository.save(user);
        }
        return user;
        }


}
