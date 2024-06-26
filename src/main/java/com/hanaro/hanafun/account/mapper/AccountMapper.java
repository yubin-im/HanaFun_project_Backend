package com.hanaro.hanafun.account.mapper;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.dto.AccountResDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public AccountResDto entityToAccountResDto(AccountEntity accountEntity){
        if(accountEntity == null) return null;

        return AccountResDto.builder()
                .accountId(accountEntity.getAccountId())
                .accountNumber(accountEntity.getAccountNumber())
                .accountName(accountEntity.getAccountName())
                .balance(accountEntity.getBalance())
                .build();
    }
}
