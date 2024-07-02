package com.hanaro.hanafun.account.service;

import com.hanaro.hanafun.account.dto.AccountResDto;

import java.util.List;

public interface AccountService {
    List<AccountResDto> readAccountList(Long userId);
}
