package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        log.debug("UserController: POST /users with body {}", userDto);
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        log.debug("UserController: GET /users/{}", id);
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.debug("UserController: GET /users/");
        return userService.getAllUsers();
    }



    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        log.debug("UserController: UPDATE /users with body {}", userDto);
        userDto.setId(userId);
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        log.info("UserController: DELETE /users/{id} by id = {}", id);
        userService.deleteUserById(id);
    }

}
