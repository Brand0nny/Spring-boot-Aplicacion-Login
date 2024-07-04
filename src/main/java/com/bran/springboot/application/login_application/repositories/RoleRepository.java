package com.bran.springboot.application.login_application.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bran.springboot.application.login_application.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{

    
} 
