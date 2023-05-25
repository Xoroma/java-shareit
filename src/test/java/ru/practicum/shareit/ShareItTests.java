package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase()
class ShareItTests {
    @Mock
	UserService userService;
	@Mock
	ItemService itemService;

	@Mock
	BookingService bookingService;
	@Mock
	UserRepository userRepository;
	@MockBean
	ItemRepository itemRepository;
	@MockBean
	CommentRepository commentRepository;

	User user;
	ItemDto itemDto;

	@BeforeEach
	void beforeEach() {

		userService = new UserService(userRepository);
		itemService = new ItemServiceImpl(itemRepository, userService, bookingService, commentRepository);
		user = new User(1, "testovich", "test@test.com");
		itemDto = new ItemDto(1, "TestItem", "DescriptionTest", true,0);
	}

	@Test
	void getById() {

		when(userRepository.findById(anyLong()))
				.thenReturn(Optional.ofNullable(user));
		 User actual = userService.getUserById(1);

		Assertions.assertEquals(actual, user);

	}


	@Test
	void updateUser()  {

		when(userRepository.save(any()))
				.thenReturn(user);
		userService.add(user);

		User newUser = new User(1, "Updated", "test@test.com");
		when(userRepository.save(any()))
				.thenReturn(newUser);
		when(userRepository.findById(anyLong()))
				.thenReturn(Optional.of(newUser));
		userService.update(newUser, 1);

		Assertions.assertEquals(userService.getUserById(1), newUser);

	}

	@Test
	void deleteUser()  {
		when(userRepository.save(any()))
				.thenReturn(user);
		userService.add(user);
		userService.delete(1);

		Assertions.assertThrows(NotFoundException.class, () -> userService.getUserById(1));
	}
}
