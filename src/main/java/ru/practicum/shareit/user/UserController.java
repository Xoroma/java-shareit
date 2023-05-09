package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.service.UserService;

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
        log.info("Request POST /users");
        return userService.add(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Request GET /users");
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable long id)  {
        log.info(String.format("Request GET /users/%s", id));
        return userService.getUserById(id);
    }

    @PatchMapping(value = "/{id}")
    public User update(@RequestBody User user, @PathVariable long id) {
        log.info(String.format("Request PATCH /users/%s", id));
        return userService.update(user, id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {
        log.info(String.format("Request DELETE /users/%s", id));
        userService.delete(id);
    }
}

