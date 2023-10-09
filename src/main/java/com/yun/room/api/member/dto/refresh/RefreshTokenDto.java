package com.yun.room.api.member.dto.refresh;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenDto {
    @NotEmpty
    String refreshToken;
}
