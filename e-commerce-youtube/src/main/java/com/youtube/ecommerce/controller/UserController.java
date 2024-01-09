package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.UserDTO;
import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }

    @GetMapping({"/getAllUsers"})
    @PreAuthorize("hasRole('Admin')")
    public List<UserDTO> getAllUsers(){
        return  userService.getAllUser();
    }
    @DeleteMapping({"/deleteUser/{id}"})
    @PreAuthorize("hasRole('Admin')")
    public void deleteUser(@PathVariable("id") Long id){

       userService.deleteById(id);
    }

}
