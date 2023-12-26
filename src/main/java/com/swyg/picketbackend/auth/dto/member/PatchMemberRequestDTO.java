package com.swyg.picketbackend.auth.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PatchMemberRequestDTO {

    private String nickname; // 닉네임

}
