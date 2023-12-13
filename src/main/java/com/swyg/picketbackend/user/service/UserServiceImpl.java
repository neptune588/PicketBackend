package com.swyg.picketbackend.user.service;

import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import com.swyg.picketbackend.user.domain.User;
import com.swyg.picketbackend.user.dto.SignupDTO;
import com.swyg.picketbackend.user.repository.UserRepository;
import com.swyg.picketbackend.user.util.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(SignupDTO signupDTO) {

        if(userRepository.findByEmail(signupDTO.getEmail()).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        if(userRepository.findByNickname(signupDTO.getNickname()).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        // 비밀번호 암호화
        String password = passwordEncoder.encode(signupDTO.getPassword());

        // DTO -> Entity
        User user = User.builder()
                .email(signupDTO.getEmail())
                .password(password)
                .nickname(signupDTO.getNickname())
                .role(Role.USER)
                .build();
        
        // 일반 회원 저장
        userRepository.save(user); 
    }
}
