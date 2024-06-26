package com.hanaro.hanafun.user.service.impl;

import com.hanaro.hanafun.authentication.JwtUtil;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.dto.IsHostResDto;
import com.hanaro.hanafun.user.dto.LoginReqDto;
import com.hanaro.hanafun.user.dto.LoginResDto;
import com.hanaro.hanafun.user.dto.PointResDto;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import com.hanaro.hanafun.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResDto login(LoginReqDto loginReqDto) throws UserNotFoundException{
        UserEntity userEntity = userRepository.findByPassword(loginReqDto.getPassword())
                .orElseThrow(() -> new UserNotFoundException());
        String generatedJwt = jwtUtil.createToken(userEntity.getUsername(), userEntity.getUserId());

        return new LoginResDto().builder()
                .jwt(generatedJwt)
                .userName(userEntity.getUsername())
                .build();
    }

    @Override
    public PointResDto readPoint(Long userId) throws UserNotFoundException{
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        return new PointResDto().builder()
                .point(userEntity.getPoint())
                .build();
    }

    @Override
    public IsHostResDto readIsHost(Long userId) throws UserNotFoundException{
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        return new IsHostResDto().builder()
                .isHost(userEntity.getIsHost())
                .build();
    }
}
