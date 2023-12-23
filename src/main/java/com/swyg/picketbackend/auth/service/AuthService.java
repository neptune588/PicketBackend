package com.swyg.picketbackend.auth.service;

import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.domain.RefreshToken;
import com.swyg.picketbackend.auth.dto.auth.req.LoginDTO;
import com.swyg.picketbackend.auth.dto.auth.req.SignupRequestDTO;
import com.swyg.picketbackend.auth.dto.auth.req.TokenRequestDTO;
import com.swyg.picketbackend.auth.dto.auth.res.SignupResponseDTO;
import com.swyg.picketbackend.auth.dto.auth.res.TokenResponseDTO;
import com.swyg.picketbackend.auth.jwt.TokenProvider;
import com.swyg.picketbackend.auth.repository.MemberRepository;
import com.swyg.picketbackend.auth.repository.RefreshTokenRepository;
import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    // 회원 가입 이메일 중복 검사
    public void checkEmail(String email) {
        boolean result = memberRepository.existsByEmail(email);
        log.info(result);
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    // 회원 가입 닉네임 중복 검사
    public void checkNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }


    // 회원 가입 서비스
    @Transactional
    public void signup(SignupRequestDTO signupRequestDTO) throws CustomException {
        if (memberRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        if (memberRepository.existsByNickname(signupRequestDTO.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        Member member = signupRequestDTO.toMember(passwordEncoder);

        memberRepository.save(member);
    }


    // 로그인 서비스
    @Transactional
    public TokenResponseDTO login(LoginDTO loginDTO) {
        // 1. Login email/Password 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDTO.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 PrincipalUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("authentication.getName() : " + authentication.getName());

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponseDTO tokenResponseDto = tokenProvider.generateTokenDto(authentication);


        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenResponseDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenResponseDto;
    }

    @Transactional
    public TokenResponseDTO reissue(TokenRequestDTO tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenResponseDTO tokenResponseDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenResponseDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenResponseDto;
    }

    @Transactional
    public SignupResponseDTO findMember(Long id) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 로그인한 유저 아이디 조회


        if (!currentMemberId.equals(id)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_ACCESS); // 로그인한 사용자가 아니면 접근 권한 없음 throw exception
        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // entity -> dto
        return SignupResponseDTO.of(member);
    }


}
