package com.swyg.picketbackend.board.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swyg.picketbackend.auth.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;


}
