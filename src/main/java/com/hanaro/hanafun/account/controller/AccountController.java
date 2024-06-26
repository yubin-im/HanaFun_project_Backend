package com.hanaro.hanafun.account.controller;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;
    private final AccountRepository accountRepository;
    @GetMapping("/test")
    public ResponseEntity<Optional<AccountEntity>> test(){
        Optional<AccountEntity> test = accountRepository.findById(1L);
        return ResponseEntity.ok(test);
    }
}
