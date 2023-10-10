package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class util {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public util(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void createRoleAndUser() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);

        User admin = new User("ADMIN_NAME", "ADMIN_SURNAME",
                "admin", "admin", "admin@mail.ru", Set.of(adminRole));

        User user = new User("USER_NAME", "USER_SURNAME",
                "user", "user", "user@mail.ru", Set.of(userRole));

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
