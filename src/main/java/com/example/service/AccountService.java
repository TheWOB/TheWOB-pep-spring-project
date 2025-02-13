package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;

@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account){
        if(doesNotExist(account) != null || account.getPassword().length() < 4){
            return null;
        }
        return accountRepository.save(account);
    }

    public Account login(Account account){
        Account a = doesNotExist(account);
        if(a == null){
            return null;
        }
        if(a.getPassword().equals(account.getPassword())){
            return a;
        }
        return null;
    }

    public boolean checkDuplicate(Account account){
        List<Account> accounts = accountRepository.findAll();

        for(Account a : accounts){
            if(a.getUsername().equals(account.getUsername())){
                return true;
            }
        }
        return false;
    }

    public Account doesNotExist(Account account){
        if(account.getUsername().equals("")){
            return null;
        }
        List<Account> accounts = accountRepository.findAll();

        for(Account a : accounts){
            if(a.getUsername().equals(account.getUsername())){
                return a;
            }
        }

        return null;
    }

    public Boolean checkId(Integer id){
        List<Account> accounts = accountRepository.findAll();
        for(Account a : accounts){
            if(a.getAccountId().equals(id)){
                return true;
            }
        }
        return false;
    }

}
