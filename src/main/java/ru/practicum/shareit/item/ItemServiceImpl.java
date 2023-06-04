package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.GetItemDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.model.dto.ItemMapper.*;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto addItem(ItemDto dto, long ownerId) {
        log.info("Добавлен предмет");
        if (userRepository.existsById(ownerId)) {
            return toItemDto(itemRepository.save(toItem(dto, ownerId)));
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public ItemDto patchItem(ItemDto dto, long ownerId, long itemId) {
        dto.setId(itemId);
        if (getItemOwnerId(itemId) != ownerId) {
            throw new NotFoundException();
        }
        if (itemRepository.findById(itemId).isPresent()) {
            Item oldItem = itemRepository.findById(itemId).get();

            if (dto.getName() == null) {
                dto.setName(oldItem.getName());
            }
            if (dto.getDescription() == null) {
                dto.setDescription(oldItem.getDescription());
            }
            if (dto.getAvailable() == null) {
                dto.setAvailable(oldItem.isAvailable());
            }
        } else {
            throw new NotFoundException();
        }
        log.info("Обновлен предмет с id " + itemId);
        return toItemDto(itemRepository.save(toItem(dto, ownerId)));
    }

    public long getItemOwnerId(long itemId) {
        return itemRepository.getReferenceById(itemId).getOwnerId();
    }

    @Override
    public GetItemDto getItem(long itemId, long ownerId) {

        if (itemRepository.existsById(itemId)) {
            Item item = itemRepository.getReferenceById(itemId);
            List<Comment> comments = commentRepository.findAllByItemId(itemId);
            log.info("Получен предмет с id " + itemId);

            List<Booking> bookings;

            if (item.getOwnerId() == ownerId) {
                bookings = new ArrayList<>(bookingRepository.allBookingsForItem(itemId,
                        Sort.by(Sort.Direction.ASC, "start")));
            } else {
                bookings = Collections.emptyList();
            }

            if (bookings.size() != 0 && item.getOwnerId() == ownerId) {
                return toGetItemDto(
                        item, bookings, comments
                );
            } else {
                return toGetItemDto(item, null, null, comments);
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public List<GetItemDto> getAllItemsByOwner(long ownerId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of((from / size), size);
        List<GetItemDto> allItems =
                itemRepository.findAll(pageRequest)
                        .stream()
                        .filter(l -> l.getOwnerId() == ownerId)
                        .map(l -> ItemMapper.toGetItemDto(l, null, null, null))
                        .sorted(Comparator.comparing(GetItemDto::getId))
                        .collect(Collectors.toList());

        List<Comment> allCommentsByItemsOwner = commentRepository.findAllByItemsOwnerId(ownerId);
        List<Booking> allBookingsByItemsOwner = bookingRepository.findAllByItemsOwnerId(ownerId);

        for (GetItemDto item : allItems) {

            List<Comment> comments = allCommentsByItemsOwner
                    .stream()
                    .filter(l -> l.getItemId() == item.getId())
                    .collect(Collectors.toList());
            item.setComments(comments);

            List<Booking> bookings = allBookingsByItemsOwner
                    .stream()
                    .filter(l -> l.getItemId() == item.getId())
                    .collect(Collectors.toList());

            if (bookings.size() != 0) {
                item.setLastBooking(bookings.get(0));
                item.setNextBooking(bookings.get(bookings.size() - 1));
            }
        }
        return allItems;
    }

    @Override
    public List<ItemDto> searchItem(String text, long ownerId, Integer from, Integer size) {
        if (!text.equals("")) {
            PageRequest pageRequest = PageRequest.of((from / size), size);
            return itemRepository.search(text, pageRequest)
                    .stream()
                    .filter(Item::isAvailable)
                    .map(ItemMapper::toItemDto)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    @Override
    public Comment addComment(Comment dto, long itemId, long authorId) {
        if (bookingRepository.bookingsForItemAndBookerPast(authorId, itemId, LocalDateTime.now()).size() != 0) {
            User author = userRepository.findById(authorId).get();
            Comment comment = new Comment();
            comment.setAuthorId(authorId);
            comment.setItemId(itemId);
            comment.setText(dto.getText());
            comment.setCreated(LocalDateTime.now());
            comment.setAuthorName(author.getName());
            return commentRepository.save(comment);
        } else {
            throw new BadRequestException();
        }
    }
}
