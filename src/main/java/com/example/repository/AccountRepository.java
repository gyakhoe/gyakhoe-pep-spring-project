package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
    
    boolean existsByUsername(String username);

    Account findByUsername(String username);

    boolean existsByAccountId(Integer accountId);
    
}
