package com.shopme.user.service;

import com.shopme.application.exception.UserNotFoundException;
import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.repository.RoleRepository;
import com.shopme.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
        boolean isUpdatingUser = (user.getCode() != null);

        if (isUpdatingUser){
            User existingUser = userRepository.findById(user.getCode()).get();

            if (user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            }else{
                encodePassword(user);
            }
        }else{
            encodePassword(user);
        }
        userRepository.save(user);
    }
    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(String email,Integer code){
       User userByEmail = userRepository.getUserByEmail(email);
       boolean existingUserEmail = (code == null);

       if (existingUserEmail){
           if (userByEmail != null)
               return false;
       }

       if (userByEmail == null){
            return true;
        } else if (userByEmail.getCode() != code) {
            return false;
        }

       return true;

    }
    public User getUserByCode(Integer code) throws UserNotFoundException {
        try {
            return userRepository.findById(code).get();
        } catch (NoSuchElementException exception){
            throw new UserNotFoundException("Usuário não foi localizado no banco de dados" + code);
        }
    }
    public void deleteByCode(Integer code) throws UserNotFoundException {
        Long countByCode = userRepository.countBycode(code);
        if (countByCode == null || countByCode == 0){
            throw new UserNotFoundException("");
        }
        userRepository.deleteById(code);
    }

}
