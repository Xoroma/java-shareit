package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        log.info("POST /users");
        return userService.add(user);
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable long id) {
        log.info(String.format("GET /users/%s", id));
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("GET /users");
        return userService.getAllUsers();
    }

    @PatchMapping(value = "/{id}")
    public User update(@RequestBody User user, @PathVariable long id) {
        log.info(String.format("PATCH /users/%s", id));
        return userService.update(user, id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {
        log.info(String.format("DELETE /users/%s", id));
        userService.delete(id);
    }
}

