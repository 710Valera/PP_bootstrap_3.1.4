package ru.opolonina.kataPP.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.opolonina.kataPP.model.Role;
import ru.opolonina.kataPP.model.User;
import ru.opolonina.kataPP.service.RoleService;
import ru.opolonina.kataPP.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final String REDIRECT = "redirect:/admin";

    @Autowired
    public AdminController(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showAllUsers(@ModelAttribute ("user") User user, Principal principal, Model model) {
        User authenticatedUser = userService.findByUsername(principal.getName());

        model.addAttribute ("authenticatedUser", authenticatedUser);
        model.addAttribute ("roleOfAuthenticatedUser", authenticatedUser.getRoles());
        model.addAttribute("users", userService.findAll());
        model.addAttribute( "AllRoles", roleService.findAll());
        return "admin-page";
    }

    @GetMapping("/user-profile/{id}")
    public String findUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("AllRoles", user.getRoles());
        return "user-page";
    }

    @GetMapping("/{id}")
    public String editUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("AllRoles", roleService.findAll());
        return REDIRECT;
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User updateUser, @PathVariable("id") int id) {
        userService.updateUser(updateUser, id); //Находим по id того юзера, которого надо изменить
        return REDIRECT;
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return REDIRECT;
    }


    @GetMapping("/new")
    public String saveNewUserForm(Model model, User user) {
        model.addAttribute("user", new User());
        List<Role> roles = (List<Role>) roleService.findAll();
        model.addAttribute("AllRoles", roles);
        return REDIRECT;
    }


    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        System.out.println("Контроллер" + user.toString());
        return REDIRECT;
    }
}