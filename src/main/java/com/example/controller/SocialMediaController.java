package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.*;
import com.example.service.*;
import com.example.repository.*;

import java.util.List;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account){
        if(accountService.checkDuplicate(account)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Account a = accountService.addAccount(account);
        if(a == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(a);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account a = accountService.login(account);
        if(a == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(a);
        }
        return ResponseEntity.status(HttpStatus.OK).body(a);
    }

    @PatchMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(accountService.checkId(message.getPostedBy()) == false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Message m = messageService.addMessage(message);
        if(m == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
        }
        return ResponseEntity.status(HttpStatus.OK).body(m);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessages());
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesForUser(@PathVariable Integer accountId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesByUser(accountId));
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageGivenMessageId(@PathVariable Integer messageId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageByID(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable int messageId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.deleteByID(messageId));
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message message, @PathVariable int messageId){
        if(messageService.updateMessage(message.getMessageText(), messageId) == 1){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
