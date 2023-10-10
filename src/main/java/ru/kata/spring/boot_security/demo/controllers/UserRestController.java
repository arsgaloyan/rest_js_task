package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public ResponseEntity<User> registartionPage(@RequestBody User user) {
        userService.getAllUsers();
        roleService.getRoles();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> perfomRegistration(@RequestBody User user, BindingResult bindingResult) {

        userService.saveUser(user);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUserInfo(@AuthenticationPrincipal User userPrincipal) {

        return ResponseEntity.ok(userPrincipal);
    }

}
