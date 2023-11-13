package com.yun.room.api.member.dto.inspection_req;

import com.yun.room.api.house.dto.search_houses.RoomDto;
import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;

import com.yun.room.domain.inspection_req_status_type.entity.InspectionReqStatusType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
public class MemberInspectionReqResponseDto {
    private Long inspectionReqId;
    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Boolean isDeletedByTenant;
    private RoomDto room;
    private MemberReqStatusTypeDto memberReqStatusTypeDto;
    private String message;

    public MemberInspectionReqResponseDto(InspectionReq inspectionReq) {
        this.inspectionReqId = inspectionReq.getId();
        this.inspectionDateTime = inspectionReq.getInspectionDateTime();
        this.moveInDate = inspectionReq.getMoveInDate();
        this.isDeletedByTenant = inspectionReq.getIsDeletedByTenant();
        this.room = new RoomDto(inspectionReq.getRoom());
        InspectionReqStatus mostRecentStatus = inspectionReq.getInspectionReqStatuses()
                .stream()
                .max(Comparator.comparing(InspectionReqStatus::getCreatedDate))
                .orElse(null);
        InspectionReqStatusType inspectionReqStatusType = mostRecentStatus.getInspectionReqStatusType();
        this.memberReqStatusTypeDto = new MemberReqStatusTypeDto(inspectionReqStatusType);

        this.message = mostRecentStatus.getMessage();
    }
}
