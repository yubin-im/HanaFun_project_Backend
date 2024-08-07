package com.hanaro.hanafun.account.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.dto.AccountResDto;
import com.hanaro.hanafun.account.dto.PwReqDto;
import com.hanaro.hanafun.account.dto.PwResDto;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.account.mapper.AccountMapper;
import com.hanaro.hanafun.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public List<AccountResDto> readAccountList(Long userId) {
        List<AccountEntity> accountEntityList = accountRepository.findByUserEntityUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException());
        if(accountEntityList.isEmpty()) throw new AccountNotFoundException();

        return accountEntityList.stream().map(AccountMapper::entityToAccountResDto).toList();
    }

    @Override
    public PwResDto checkAccountPw(PwReqDto pwReqDto) {
        AccountEntity accountEntity = accountRepository.findById(pwReqDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException());

        return new PwResDto().builder()
                .check(accountEntity.getPassword().equals(pwReqDto.getPassword()))
                .build();
    }
}
