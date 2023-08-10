package com.shopme.user.controller;

import com.shopme.application.exception.UserNotFoundException;
import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

import static com.shopme.user.service.UserService.*;

@Controller
public class UserController {
    private final static String URI_REDIRECT_USER = "redirect:/usuarios";
    private final static String VIEW_USER_REDIRECT = "users";
    private final static String VIEW_USER_FORM_REDIRECT = "form_user";

    @Autowired
    private UserService userService;

    @GetMapping("/usuarios")
    public String listAll(Model model){
        return listByPage(FIRST_PAGE,model);
    }

    @GetMapping("/usuarios/pagina/{pageNum}")
    public String listByPage(@PathVariable(name= "pageNum") int pageNum, Model model){
        Page<User> listUserPages = userService.listByPage(pageNum);
        List<User> listUsers = listUserPages.getContent();

        long initialCount = (FIRST_PAGE) * USERS_PER_PAGE + FIRST_PAGE;
        long finalCount = initialCount + USERS_PER_PAGE - FIRST_PAGE;

        if (finalCount > listUserPages.getTotalElements()){
            finalCount = listUserPages.getTotalElements();
        }

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("initialCount",initialCount);

        model.addAttribute("finalCount",finalCount);
        model.addAttribute("totalPages",listUserPages.getTotalPages());

        model.addAttribute("totalItems",listUserPages.getTotalElements());
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
        return VIEW_USER_FORM_REDIRECT;
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(User user, RedirectAttributes redirectAttributes,
                                @RequestParam("image")MultipartFile multipartFile) throws IOException {

        userService.checkFileImageUpload(multipartFile,user);
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
            return VIEW_USER_FORM_REDIRECT;

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
