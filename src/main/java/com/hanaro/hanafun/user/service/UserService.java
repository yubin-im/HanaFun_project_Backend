package com.hanaro.hanafun.user.service;

import com.hanaro.hanafun.user.dto.IsHostResDto;
import com.hanaro.hanafun.user.dto.LoginReqDto;
import com.hanaro.hanafun.user.dto.LoginResDto;
import com.hanaro.hanafun.user.dto.PointResDto;

public interface UserService {
    LoginResDto login(LoginReqDto loginReqDto);

    PointResDto readPoint(Long userId);

    IsHostResDto readIsHost(Long userId);

}
