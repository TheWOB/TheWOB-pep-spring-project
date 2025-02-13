package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message){
        if(message.getMessageText().equals("") || message.getMessageText().length() > 255){
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByID(Integer id){
        if(messageRepository.existsById(id)){
            List<Message> messages = messageRepository.findAll();
            for(Message m : messages){
                if(m.getMessageId().equals(id)){
                    return m;
                }
            }
        }
        return null;
    }

    public Integer deleteByID(int id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
    }

    public int updateMessage(String messageText, int id){
        if(messageRepository.existsById(id) && !messageText.equals("") && messageText.length() < 255){
            Message message = messageRepository.getById(id);
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByUser(Integer postedBy){
        List<Message> messages = messageRepository.findAll();
        List<Message> userMessages = new ArrayList<Message>();

        for(Message m : messages){
            if(m.getPostedBy().equals(postedBy)){
                userMessages.add(m);
            }
        }

        return userMessages;
    }
}
