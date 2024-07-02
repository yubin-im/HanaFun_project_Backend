package com.hanaro.hanafun.account.service;

import com.hanaro.hanafun.account.dto.AccountResDto;
import com.hanaro.hanafun.account.dto.PwReqDto;
import com.hanaro.hanafun.account.dto.PwResDto;

import java.util.List;

public interface AccountService {
    List<AccountResDto> readAccountList(Long userId);
    PwResDto checkAccountPw(PwReqDto pwReqDto);
}
