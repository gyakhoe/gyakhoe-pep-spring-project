package com.example.controller;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


 @RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {

        if(account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(account.getPassword() == null || account.getPassword().trim().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(accountService.isUsernameTaken(account.getUsername().trim())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Account savedAccount = accountService.registerAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        
        // Validation 
        if(account.getUsername() == null && account.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Account savedAccount = accountService.login(account.getUsername(), account.getPassword());
        if(savedAccount != null) {
            return ResponseEntity.ok(savedAccount);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {

        if(!isValidMessageText(message.getMessageText())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(!accountService.isValidUserId(message.getPostedBy())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        final Message savedMessage = messageService.createMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.selectAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.selectMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteByMessageId(@PathVariable Integer messageId) {
        int result = messageService.deleteMessage(messageId);
        if(result == 1) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> patchMessageTextByMessageId(@PathVariable Integer messageId, @RequestBody Map<String, String>  messageBody){
        final String messageText = messageBody.get("messageText");

        if(!isValidMessageText(messageText)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        int result = messageService.updateMessage(messageId, messageText);
        if(result != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(result);

    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByPostedBy(@PathVariable Integer accountId){
        return ResponseEntity.ok(messageService.selectAllByPostedBy(accountId));
    }

    private boolean isValidMessageText(String messageText){
        if(messageText == null || messageText.isBlank() ) {
            return false;
        }
        
        if(messageText.length() > 255) {
            return false;
        }

     

        return true;
    }

}
