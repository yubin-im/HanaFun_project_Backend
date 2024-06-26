package com.hanaro.hanafun.user.service.impl;

import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.dto.IsHostResDto;
import com.hanaro.hanafun.user.dto.LoginReqDto;
import com.hanaro.hanafun.user.dto.LoginResDto;
import com.hanaro.hanafun.user.dto.PointResDto;
import com.hanaro.hanafun.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;

    @Override
    public LoginResDto login(LoginReqDto loginReqDto) {
        UserEntity userEntity = userRepository.findByPassword(loginReqDto.getPassword())
                .orElseThrow();
//        String generatedJwt = jwtUtil.generateAccessToken(userEntity.getUserId());

        return new LoginResDto().builder()
                .jwt("generatedJwt")
                .userName(userEntity.getUserName())
                .build();
    }

    @Override
    public PointResDto getPoint(Long userId) {
        return null;
    }

    @Override
    public IsHostResDto isHost(Long userId) {
        return null;
    }
}
