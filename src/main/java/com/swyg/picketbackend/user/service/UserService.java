package com.swyg.picketbackend.user.service;

import com.swyg.picketbackend.user.dto.SignupDTO;

public interface UserService {

    public void register(SignupDTO signupDTO);
}
