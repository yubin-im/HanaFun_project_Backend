package com.hanaro.hanafun.host.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.host.dto.*;
import com.hanaro.hanafun.host.exception.HostNotFoundException;
import com.hanaro.hanafun.host.mapper.HostMapper;
import com.hanaro.hanafun.host.service.HostService;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final LessonRepository lessonRepository;
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

    @Override
    public HostInfoResDto readHostInfo(Long userId) {
        HostEntity hostEntity = hostRepository.findByUserEntityUserId(userId).orElseThrow(() -> new HostNotFoundException());
        AccountEntity accountEntity = hostEntity.getAccountEntity();
        HostAccountDto hostAccountDto = HostAccountDto.builder()
                .accountId(accountEntity.getAccountId())
                .accountName(accountEntity.getAccountName())
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .build();

        List<LessonEntity> lessonEntityList = lessonRepository.findByHostEntityHostId(hostEntity.getHostId()).orElseThrow();
        List<HostLessonDto> hostLessonDtoList = lessonEntityList.stream().map(HostMapper::LessonEntityToHostLessonDto).toList();

        return new HostInfoResDto().builder()
                .account(hostAccountDto)
                .lessonList(hostLessonDtoList)
                .build();
    }
}
