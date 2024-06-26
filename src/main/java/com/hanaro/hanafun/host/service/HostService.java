package com.hanaro.hanafun.host.service;

import com.hanaro.hanafun.host.dto.CreateHostReqDto;
import com.hanaro.hanafun.host.dto.CreateHostResDto;

public interface HostService {
    CreateHostResDto createHost(Long userId, CreateHostReqDto createHostReqDto);
}
