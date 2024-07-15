package com.bran.springboot.application.login_application.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bran.springboot.application.login_application.entities.User;
@Repository
public interface UserRepository extends CrudRepository<User,Long>{

    Optional<User> findByUsername(String username);
 

    
} 
