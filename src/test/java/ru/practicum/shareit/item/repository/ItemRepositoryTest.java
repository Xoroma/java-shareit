package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
class ItemRepositoryTest {
    User createdUser1;
    User createdUser2;
    Item createdItem1;
    Item createdItem2;
    ItemRequest createdRequest1;
    ItemRequest createdRequest2;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository requestRepository;

    @BeforeEach
    public void beforeEach() {
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@mail.com");
        createdUser1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("user2");
        user2.setEmail("user2@mail.com");
        createdUser2 = userRepository.save(user2);
        ItemRequest request1 = new ItemRequest();
        request1.setDescription("testDescription1");
        request1.setRequesterId(user1.getId());
        request1.setCreated(LocalDateTime.now().minusDays(5));
        createdRequest1 = requestRepository.save(request1);

        ItemRequest request2 = new ItemRequest();
        request2.setDescription("testDescription1");
        request2.setRequesterId(user1.getId());
        request2.setCreated(LocalDateTime.now().minusDays(2));
        createdRequest2 = requestRepository.save(request2);

        Item item1 = new Item();
        item1.setName("testItem");
        item1.setDescription("testItemDescription");
        item1.setAvailable(true);
        item1.setOwnerId(createdUser1.getId());
        item1.setRequestId(createdRequest1.getId());
        createdItem1 = itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("testItem2");
        item2.setDescription("testItem2Description");
        item2.setAvailable(true);
        item2.setOwnerId(createdUser2.getId());
        item2.setRequestId(createdRequest2.getId());
        createdItem2 = itemRepository.save(item2);
    }

    @Test
    void findByOwnerId_isValid() {
        assertEquals(createdItem1, itemRepository.findById(createdUser1.getId()).get());
    }

    @Test
    void findItemsByQuery_isValid() {
        assertEquals(List.of(createdItem2), itemRepository.search("testItem2", Pageable.unpaged()));
    }

    @Test
    void getItemsByQuery_isInvalid() {
        assertEquals(List.of(), itemRepository.search("asdasdasgas", Pageable.unpaged()));
    }

}