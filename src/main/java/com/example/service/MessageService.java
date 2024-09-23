package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> selectAllMessages() {
        return messageRepository.findAll();
    }

    public Message selectMessageById(Integer messageId) {
        return messageRepository.getByMessageId(messageId);

    }

    @Transactional
    public int deleteMessage(Integer messageId){
        if(messageRepository.getByMessageId(messageId) != null) {
            messageRepository.deleteByMessageId(messageId);
            return 1;
        }
        return 0;
    }

    @Transactional
    public int updateMessage(Integer messageId, String messageText) {
        Message message  = messageRepository.getByMessageId(messageId);
        if(message != null) {
            message.setMessageText(messageText);
             messageRepository.save(message);
             return 1;
        }
        return 0;
    }

    public List<Message> selectAllByPostedBy(Integer postedBy){
        List<Message> messages = messageRepository.findAllByPostedBy(postedBy);
        return messages;
    }

}
