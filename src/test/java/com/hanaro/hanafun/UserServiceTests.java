package com.hanaro.hanafun;

import com.hanaro.hanafun.common.authentication.JwtUtil;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.dto.LoginReqDto;
import com.hanaro.hanafun.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceTests {

    //@Mock을 사용하여 UserRepository를 Mock 객체로 생성한다.
    @Mock
    private UserRepository userRepository;

    //@Mock을 사용하여 JwtUtil을 Mock 객체로 생성한다.
    @Mock
    private JwtUtil jwtUtil;

    //@InjectMocks를 사용하여 Mock 객체들을 UserServiceImpl에 주입한다.
    //스프링 컨텍스트와 무관하게 동작한다.
    //참고로 @Autowired는 스프링 프레임워크의 일부로서 컨텍스트에서 관리하는 빈 객체만 주입한다.
    @InjectMocks
    private UserServiceImpl userService;

    //각 테스트 실행 전에 Mock 객체들을 초기화한다.
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess(){
        //Arrange: 테스트를 위한 준비 단계

        //사용자 정보 설정
        long userId = 1L;
        String username = "이민지";
        String password = "1111";
        String generatedJwt = "";

        //UserEntity 생성
        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .build();

        //LoginReqDto 생성
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .password(password)
                .build();

        //userRepository.findByPassword(password) 호출 시 userEntity를 반환하도록 설정
        when(userRepository.findByPassword(password)).thenReturn(Optional.of(userEntity));

    }

    @Test
    void login() {
    }

    @Test
    void readPoint() {
    }

    @Test
    void readIsHost() {
    }
}
