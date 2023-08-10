package com.shopme.user.service;

import com.shopme.application.exception.UserNotFoundException;
import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.repository.RoleRepository;
import com.shopme.user.repository.UserRepository;
import com.shopme.application.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class UserService {

    public static final int USERS_PER_PAGE = 4;

    public static final int FIRST_PAGE = 1;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String MESSAGE_USER_NOT_FOUND = "Usuário não foi localizado no banco de dados";

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public List<Roles> listRoles(){
     return roleRepository.findAll();
    }

    public User salvar(User user) {
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
        return userRepository.save(user);
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
            throw new UserNotFoundException(MESSAGE_USER_NOT_FOUND + code);
        }
    }

    public Page<User> listByPage(int pageNumber){
        int actuaPage = pageNumber - FIRST_PAGE;
        Pageable pageable = PageRequest.of(actuaPage ,USERS_PER_PAGE);
        return userRepository.findAll(pageable);
    }
    public void deleteByCode(Integer code) throws UserNotFoundException {
        Long countByCode = userRepository.countBycode(code);
        if (countByCode == null || countByCode == 0){
            throw new UserNotFoundException(MESSAGE_USER_NOT_FOUND);
        }
        userRepository.deleteById(code);
    }
    public void updateStatusUserEnabled(Integer code,boolean enabled){
        userRepository.updateEnabledStatus(code,enabled);
    }

    public void checkFileImageUpload(MultipartFile multipartFile, User user) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            user.setPhotos(fileName);
            User savedUser = salvar(user);

            String uploadDir = "user-photos/" + savedUser.getCode();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            salvar(user);
        }
    }
}
