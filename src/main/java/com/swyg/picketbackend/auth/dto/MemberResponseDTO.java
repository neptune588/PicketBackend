package com.swyg.picketbackend.auth.dto;


import com.swyg.picketbackend.auth.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {

    private String email;

    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(member.getEmail());
    }
}
