package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    ItemRequestServiceImpl requestService;

    @Test
    public void testSetDescription() {
        ItemRequest itemRequest = new ItemRequest();
        String description = "test description";
        itemRequest.setDescription(description);
        assertEquals(description, itemRequest.getDescription());
    }

    @Test
    void addRequest() {
        long userId = 1L;
        ItemRequestDto dto = new ItemRequestDto();
        dto.setDescription("testovich");
        ItemRequest request = ItemRequestMapper.toItemRequest(dto, userId);
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        when(itemRequestRepository.save(any()))
                .thenReturn(request);

        assertEquals(request, requestService.addRequest(dto, userId));
    }

    @Test
    void getRequests() {
        long userId = 1L;
        long itemId = 1L;
        long ownerId = 1L;
        ItemDto itemDto = new ItemDto(itemId, "TestItem", "DescriptionTest", true, 0);
        Item item = ItemMapper.toItem(itemDto, ownerId);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setDescription("testovich");
        ItemRequest request = ItemRequestMapper.toItemRequest(dto, userId);
        GetItemRequestDto getItemRequestDto = ItemRequestMapper.toGetItemRequestDto(request);
        when(itemRequestRepository.findAllByRequesterId(anyLong(), any()))
                .thenReturn(List.of(request));
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        when(itemRepository.findAllByRequesterId(anyLong()))
                .thenReturn(List.of(item));

        assertEquals(List.of(getItemRequestDto).get(0).getDescription(),
                requestService.getRequests(userId).get(0).getDescription());
    }

    @Test
    void getRequestsPageable() {
        long userId = 1L;
        long itemId = 1L;
        long ownerId = 1L;
        ItemDto itemDto = new ItemDto(itemId, "TestItem", "DescriptionTest", true, 0);
        Item item = ItemMapper.toItem(itemDto, ownerId);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setDescription("testovich");
        ItemRequest request = ItemRequestMapper.toItemRequest(dto, userId);
        GetItemRequestDto getItemRequestDto = ItemRequestMapper.toGetItemRequestDto(request);
        final Page<ItemRequest> page = new PageImpl<>(List.of(request));
        when(itemRequestRepository.findAllByRequesterIdIsNot(anyLong(), any()))
                .thenReturn(page);
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        when(itemRepository.findAllByRequesterIdIsNot(anyLong()))
                .thenReturn(List.of(item));

        assertEquals(getItemRequestDto.getDescription(),
                requestService.getRequestsPageable(userId, 0, 10).get(0).getDescription());
    }

    @Test
    void getRequestById() {
        long userId = 1L;
        long itemId = 1L;
        long ownerId = 1L;
        ItemDto itemDto = new ItemDto(itemId, "TestItem", "DescriptionTest", true, 0);
        Item item = ItemMapper.toItem(itemDto, ownerId);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setDescription("testovich");
        ItemRequest request = ItemRequestMapper.toItemRequest(dto, userId);
        GetItemRequestDto getItemRequestDto = ItemRequestMapper.toGetItemRequestDto(request);
        when(itemRequestRepository.existsById(anyLong()))
                .thenReturn(true);
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        when(itemRequestRepository.findById(anyLong()))
                .thenReturn(Optional.of(request));
        when(itemRepository.findAllByRequestId(anyLong()))
                .thenReturn(List.of(item));

        assertEquals(getItemRequestDto.getDescription(),
                requestService.getRequestById(userId, 1).getDescription());
    }
}
