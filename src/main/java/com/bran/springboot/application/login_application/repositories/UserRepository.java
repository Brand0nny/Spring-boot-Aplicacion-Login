package com.bran.springboot.application.login_application.repositories;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bran.springboot.application.login_application.entities.User;
@Repository
public interface UserRepository extends CrudRepository<User,Long>{
 

    
} 
