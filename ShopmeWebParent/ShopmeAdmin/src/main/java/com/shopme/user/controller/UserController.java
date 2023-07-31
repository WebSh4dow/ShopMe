package com.shopme.user.controller;

import com.shopme.application.exception.UserNotFoundException;
import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private final static String URI_REDIRECT_USER = "redirect:/usuarios";
    private final static String VIEW_USER_REDIRECT = "users";
    private final static String FORM_USER_REDIRECT = "form_user";

    @GetMapping("/usuarios")
    public String listAll(Model model){
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers",listUsers);
        return VIEW_USER_REDIRECT;
    }
    @GetMapping("/usuarios/novo")
    public String cadastrarUsuarios(Model model){
        List<Roles> listRoles = userService.listRoles();
        User user = new User();

        user.setEnabled(true);
        model.addAttribute("user",user);
        model.addAttribute("listRoles",listRoles);
        model.addAttribute("pageTitle","Cadastro de Usuários");
        return FORM_USER_REDIRECT;
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(User user, RedirectAttributes redirectAttributes){
        userService.salvar(user);
        redirectAttributes.addFlashAttribute("message","Usuário Cadastrado com sucesso.");
        return URI_REDIRECT_USER;
    }

    @GetMapping("/usuarios/editar/{code}")
    public String editUser(@PathVariable(name = "code") Integer code,
                           RedirectAttributes redirectAttributes,
                           Model model){
        try {
            User user = userService.getUserByCode(code);
            List<Roles> listRoles = userService.listRoles();
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle",
                    "Cadastro de Usuários:"+ " " + user.getFirstName() + " " + user.getLastName());
            return "form_user";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return URI_REDIRECT_USER;
    }

    @GetMapping("/usuarios/remover/{code}")
    public String removeUser(@PathVariable(name = "code")Integer code,
                             RedirectAttributes redirectAttributes){
        try {
            userService.deleteByCode(code);
            redirectAttributes.addFlashAttribute("message","O usuário com o seguinte codigo:" +
                   " " + code + " " + "foi removido com sucesso");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());

        }
        return URI_REDIRECT_USER;
    }

    @GetMapping("usuarios/{code}/update/{status}")
    public String updateUserStatusEnabledStatus(@PathVariable(name = "code") Integer code,
                                                @PathVariable(name = "status")boolean active,
                                                RedirectAttributes redirectAttributes){
    userService.updateStatusUserEnabled(code,active);
    String status = active ? "ativo" : "inativo";
    String message = "O usuário com codigo:" + code + " "+ "teve seu status modificado para " + status;
    redirectAttributes.addFlashAttribute("message",message);
    return URI_REDIRECT_USER;

    }

}
