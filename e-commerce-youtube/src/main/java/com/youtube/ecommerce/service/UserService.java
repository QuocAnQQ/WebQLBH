package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dao.RoleDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.dto.UserDTO;
import com.youtube.ecommerce.entity.Role;
import com.youtube.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setId(Long.valueOf(1));
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setId(Long.valueOf(2));
        user.setUserName("raj123");
        user.setUserPassword(getEncodedPassword("raj@123"));
        user.setUserFirstName("raj");
        user.setUserLastName("sharma");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public List<UserDTO> getAllUser(){
        List<User> users= (List<User>) userDao.findAll();
        List<UserDTO> userDTOS= new ArrayList<>();
        for(User user: users){
           if(user.getId() !=1){

               UserDTO userDTO= new UserDTO();
               userDTO.setId(user.getId());
               userDTO.setUserName(user.getUserName());
               userDTO.setEmail(user.getEmail());
               userDTO.setPhoneNumber(user.getPhoneNumber());
               userDTO.setRole("User");
               userDTOS.add(userDTO);
           }

        }
        return userDTOS;
    }
  public  void  deleteById(Long id){

      userDao.deleteById(id);
  }

}
