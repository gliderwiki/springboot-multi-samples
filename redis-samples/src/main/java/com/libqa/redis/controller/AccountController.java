package com.libqa.redis.controller;

import com.libqa.redis.entity.Account;
import com.libqa.redis.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author edell.lee
 * @version 2020-06-24 12:25
 * @implNote
 */
@Slf4j
@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;


    /**
     * hgetall "accounts:1d155535-00d5-4bb0-97c1-302fa09d8ba6"
     * hget "accounts:1d155535-00d5-4bb0-97c1-302fa09d8ba6" email
     * @return
     */
    @GetMapping("/add/account")
    public Account add() {
        Account account = new Account();
        account.setEmail("cafeciel@hanmail.net");
        account.setUserName("edell");

        Account saveAccount = accountRepository.save(account);

        log.info("# saveAccount = {}", account.toString());
        Optional<Account> accountOptional = accountRepository.findById(saveAccount.getId());

        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            return null;
        }
    }
}
