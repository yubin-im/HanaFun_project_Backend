package com.hanaro.hanafun.host.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.host.dto.CreateHostReqDto;
import com.hanaro.hanafun.host.dto.CreateHostResDto;
import com.hanaro.hanafun.host.service.HostService;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final HostRepository hostRepository;

    @Override
    @Transactional
    public CreateHostResDto createHost(Long userId, CreateHostReqDto createHostReqDto) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        userEntity.setIsHost(true);

        AccountEntity accountEntity = accountRepository.findById(createHostReqDto.getAccountId()).orElseThrow(() -> new AccountNotFoundException());

        HostEntity hostEntity = HostEntity.builder()
                .userEntity(userEntity)
                .introduction(createHostReqDto.getIntroduction())
                .accountEntity(accountEntity)
                .build();
        HostEntity createdHost = hostRepository.save(hostEntity);

        return new CreateHostResDto().builder()
                .hostId(createdHost.getHostId())
                .build();
    }
}
