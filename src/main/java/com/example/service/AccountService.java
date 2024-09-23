package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired    
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }

    public boolean isUsernameTaken(String username) {
        return accountRepository.existsByUsername(username);
    }

    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if(account != null && account.getPassword().equals(password)){
            return account;
        }
        return null;
    }

    public boolean isValidUserId(Integer accountId) {
        return accountRepository.existsByAccountId( accountId);
    }



}
