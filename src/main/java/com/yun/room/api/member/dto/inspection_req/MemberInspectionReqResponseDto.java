package com.yun.room.api.member.dto.inspection_req;

import com.yun.room.api.house.dto.search_houses.RoomDto;
import com.yun.room.domain.inspection_req.entity.InspectionReq;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberInspectionReqResponseDto {
    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Boolean isCurrent;
    private RoomDto room;
    private String inspectionReqStatusType;

    public MemberInspectionReqResponseDto(InspectionReq inspectionReq) {
        this.inspectionDateTime = inspectionReq.getInspectionDateTime();
        this.moveInDate = inspectionReq.getMoveInDate();
        this.isCurrent = inspectionReq.getIsCurrent();
        this.room = new RoomDto(inspectionReq.getRoom());
        this.inspectionReqStatusType = inspectionReq.getInspectionReqStatus().getType();
    }
}
