package serg.madi.userservice.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import serg.madi.userservice.entity.User;
import serg.madi.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public void saveUser(User user) {
        userService.saveUser(user);
    }

    @DeleteMapping
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}

