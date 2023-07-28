package com.shopme.user.rest;

import com.shopme.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class UserRestController {
    @Autowired
    private UserService userService;
    @PostMapping("/check-email")
    public String checkDuplicateEmail(@Param("email")String email){
        return userService.isEmailUnique(email) ? "OK" : "Duplicated";
    }
}
