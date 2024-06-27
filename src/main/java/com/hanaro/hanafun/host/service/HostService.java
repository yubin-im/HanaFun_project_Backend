package com.hanaro.hanafun.host.service;

import com.hanaro.hanafun.host.dto.CreateHostReqDto;
import com.hanaro.hanafun.host.dto.CreateHostResDto;
import com.hanaro.hanafun.host.dto.HostInfoResDto;

public interface HostService {
    CreateHostResDto createHost(Long userId, CreateHostReqDto createHostReqDto);

    HostInfoResDto readHostInfo(Long userId);
}
