package com.swyg.picketbackend.board.service;

import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.board.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class HeartService {

    private final HeartRepository heartRepository;

    private final BoardRepository boardRepository;
}
