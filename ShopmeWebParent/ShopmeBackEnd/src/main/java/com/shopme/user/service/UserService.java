package com.shopme.user.service;

import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.repository.RoleRepository;
import com.shopme.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public List<Roles> listRoles(){
     return roleRepository.findAll();
    }

    public void salvar(User user) {
        encodePassword(user);
        userRepository.save(user);
    }
    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
