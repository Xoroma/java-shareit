package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Set;

public interface UserRepository {

    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUserById(Long id);

    Set<String> getAllEmails();
}
