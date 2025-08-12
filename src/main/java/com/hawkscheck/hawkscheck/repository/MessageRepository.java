package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.Message;
import com.hawkscheck.hawkscheck.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientOrderByTimestampDesc(User recipient);
    List<Message> findBySenderOrderByTimestampDesc(User sender);
    List<Message> findByRecipient(User recipient);

    @Query("SELECT DISTINCT CASE WHEN m.sender = :user THEN m.recipient ELSE m.sender END FROM Message m WHERE m.sender = :user OR m.recipient = :user")
    List<User> findContactsByUser(@Param("user") User user);

    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.recipient = :user")
    List<User> findSendersToUser(@Param("user") User user);

    @Query("SELECT DISTINCT m.recipient FROM Message m WHERE m.sender = :user")
    List<User> findRecipientsFromUser(@Param("user") User user);
}
