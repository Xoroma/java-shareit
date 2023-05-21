package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
public class UserRepositoryTest {
    User user;
    User createdUser;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setName("testovich");
        user.setEmail("testovich@mail.com");
        createdUser = userRepository.save(user);
    }

    @Test
    void findById_isValid() {
        assertEquals(user.getName(), userRepository.findById(createdUser.getId()).get().getName());
    }
}