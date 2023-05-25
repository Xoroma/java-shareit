package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByItemId(long itemId);

    @Query("select c from Comment c" +
            " join Item i on c.itemId = i.id " +
            "join Booking b on b.itemId = i.id " +
            "join User u on i.ownerId = u.id " +
            "where i.ownerId = ?1"
    )
    List<Comment> findAllByItemsOwnerId(Long ownerId);
}
