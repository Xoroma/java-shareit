package ru.practicum.shareit.user.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    Long idCounter = 0L;

    @Override
    public User createUser(User user) {
        idCounter++;
        user.setId(idCounter);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User updateUser(User user) {
        String emailToDelete = users.get(user.getId()).getEmail();
        emails.remove(emailToDelete);
        emails.add(user.getEmail());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public void deleteUserById(Long id) {
        emails.remove(users.get(id).getEmail());
        users.remove(id);
    }

    @Override
    public Set<String> getAllEmails() {
        return emails;
    }
}