package com.bran.springboot.application.login_application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bran.springboot.application.login_application.dto.ChangePasswordForm;
import com.bran.springboot.application.login_application.entities.User;
import com.bran.springboot.application.login_application.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

    private boolean checkUsername(User user) throws Exception {
        Optional<User> userFound = userRepository.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new Exception("no disponible");
        }
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("No coinciden");
        }
        return true;
    }

    @Override
    public User createUser(User user) throws Exception {
        if (checkUsername(user) && checkPasswordMatch(user)) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("no existe"));

    }

    @Override
    public User updateUser(User user) throws Exception {
        User userFound = getUserById(user.getId());
        mapUser(user, userFound);
        return userRepository.save(userFound);

    }

    public void mapUser(User from, User to) {
        to.setName(from.getName());
        to.setLastname(from.getLastname());
        to.setUsername(from.getUsername());
        to.setEmail(from.getEmail());
        to.setRoles(from.getRoles());
    
    }

   


    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteUser(Long id) throws Exception {
        userRepository.delete(getUserById(id));
    }

    @Override
    public User changePassword(ChangePasswordForm form) throws Exception {
        User user = getUserById(form.getId());

        if(!isLoggedUserADMIN() &&  !user.getPassword().equals(form.getCurrentPassword())){

            throw new Exception("Current password invalid");
        }

     
        if (user.getPassword().equals(form.getNewPassword())) {
            throw new Exception("The new password must be different");
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            throw new Exception("The password doesnt match");
        }
        
        user.setPassword(bCryptPasswordEncoder.encode(form.getNewPassword()));
        return userRepository.save(user);

    }

    public boolean isLoggedUserADMIN() {
        return loggedUserHasRole("ROLE_ADMIN");
    }

    public boolean loggedUserHasRole(String role) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        Object roles = null;
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;

            roles = loggedUser.getAuthorities().stream()
                    .filter(x -> role.equals(x.getAuthority()))
                    .findFirst().orElse(null); // loggedUser = null;
        }
        return roles != null ? true : false;
    }

}
