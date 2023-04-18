package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.EmailAlreadyExistsException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.mapper.UserMapper.mapToUser;
import static ru.practicum.shareit.user.mapper.UserMapper.mapToUserDto;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {
        User user = mapToUser(userDto);
        if (user.getEmail() == null) {
            throw new EmailAlreadyExistsException("Email can't be  mull");
        }
        if (isEmailInvalid(user.getId(), user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already used");
        }
        return mapToUserDto(userRepository.createUser(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        if (userRepository.getUserById(id) == null) {
            throw new NotFoundException("id " + id + " is not found");
        }
        return mapToUserDto(userRepository.getUserById(id));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User userFromDto = mapToUser(userDto);
        User userFromStorage = userRepository.getUserById(userFromDto.getId());
        if (userFromDto.getName() == null) {
            userFromDto.setName(userFromStorage.getName());
        }
        if (isEmailInvalid(userFromDto.getId(), userFromDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already used");
        }
        if (userFromDto.getEmail() == null) {
            userFromDto.setEmail(userFromStorage.getEmail());
        }
        return mapToUserDto(userRepository.updateUser(userFromDto));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }


    @Override
    public boolean isEmailInvalid(Long id, String email) {
        Set<String> emails = userRepository.getAllEmails();
        if (emails.contains(email) && !userRepository.getUserById(id).getEmail().equals(email)) {
            throw new EmailAlreadyExistsException("Email Is already used");

        }
        return false;
    }
}