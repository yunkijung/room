package com.yun.room.api.member.dto.update_member;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UpdateMemberForm {

    private LocalDate birth;

    private Long genderId;

    private Long religionId;

    private Long occupationId;

    private Long raceId;

    private Long nationalityId;
}
