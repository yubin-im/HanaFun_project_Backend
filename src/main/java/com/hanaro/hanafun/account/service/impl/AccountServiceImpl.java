package com.hanaro.hanafun.account.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl {
    private final AccountRepository accountRepository;
    private Optional<AccountEntity> test(){
        return accountRepository.findById(1L);
    }
}
