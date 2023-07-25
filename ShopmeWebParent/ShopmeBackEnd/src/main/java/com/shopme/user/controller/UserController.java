package com.shopme.user.controller;

import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/usuarios")
    public String listAll(Model model){
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers",listUsers);
        return "users";
    }
    @GetMapping("/usuarios/novo")
    public String cadastrarUsuarios(Model model){
        List<Roles> listRoles = userService.listRoles();

        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user",user);
        model.addAttribute("listRoles",listRoles);
        return "form_user";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(User user, RedirectAttributes redirectAttributes){
        userService.salvar(user);
        redirectAttributes.addFlashAttribute("message","Usuário Cadastrado com sucesso.");
        return "redirect:/usuarios";
    }

}
