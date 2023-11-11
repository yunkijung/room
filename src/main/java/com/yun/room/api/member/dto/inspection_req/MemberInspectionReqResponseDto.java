package com.yun.room.api.member.dto.inspection_req;

import com.yun.room.api.house.dto.search_houses.RoomDto;
import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;
import com.yun.room.domain.inspection_req_status.type.InspectionReqStatusType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
public class MemberInspectionReqResponseDto {
    private Long inspectionReqId;
    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Boolean isCurrent;
    private RoomDto room;
    private InspectionReqStatusType inspectionReqStatusType;
    private String message;

    public MemberInspectionReqResponseDto(InspectionReq inspectionReq) {
        this.inspectionReqId = inspectionReq.getId();
        this.inspectionDateTime = inspectionReq.getInspectionDateTime();
        this.moveInDate = inspectionReq.getMoveInDate();
        this.isCurrent = inspectionReq.getIsCurrent();
        this.room = new RoomDto(inspectionReq.getRoom());
        InspectionReqStatus mostRecentStatus = inspectionReq.getInspectionReqStatuses()
                .stream()
                .max(Comparator.comparing(InspectionReqStatus::getCreatedDate))
                .orElse(null);
        this.inspectionReqStatusType = mostRecentStatus.getInspectionReqStatusType();
        this.message = mostRecentStatus.getMessage();
    }
}
