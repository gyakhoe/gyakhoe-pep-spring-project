package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

    public Message getByMessageId(Integer messageId);
    public void deleteByMessageId(Integer messageId);
    public List<Message> findAllByPostedBy(Integer postedBy);
    
}
