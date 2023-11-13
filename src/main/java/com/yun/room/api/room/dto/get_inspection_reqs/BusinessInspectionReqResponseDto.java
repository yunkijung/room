package com.yun.room.api.room.dto.get_inspection_reqs;

import com.yun.room.api.house.dto.search_houses.RoomDto;
import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;

import com.yun.room.domain.inspection_req_status_type.entity.InspectionReqStatusType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
public class BusinessInspectionReqResponseDto {
    private Long inspectionReqId;
    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Boolean isDeletedByHost;
    private RoomDto room;
    private TenantInfoDto tenant;
    private BusinessReqStatusTypeDto businessReqStatusTypeDto;
    private String message;

    public BusinessInspectionReqResponseDto(InspectionReq inspectionReq) {
        this.inspectionReqId = inspectionReq.getId();
        this.inspectionDateTime = inspectionReq.getInspectionDateTime();
        this.moveInDate = inspectionReq.getMoveInDate();
        this.isDeletedByHost = inspectionReq.getIsDeletedByHost();
        this.room = new RoomDto(inspectionReq.getRoom());

        LocalDate birth = inspectionReq.getMember().getMemberInfo().getBirth();
        int age = calculateAge(birth);

        this.tenant = new TenantInfoDto(
                inspectionReq.getMember().getMemberInfo().getGender().getType()
                , inspectionReq.getMember().getMemberInfo().getNationality().getName()
                , inspectionReq.getMember().getMemberInfo().getRace().getType()
                , inspectionReq.getMember().getMemberInfo().getOccupation().getName()
                , inspectionReq.getMember().getMemberInfo().getReligion().getName()
                , inspectionReq.getMember().getName()
                , String.valueOf(age)  // Convert age to String
        );
        InspectionReqStatus mostRecentStatus = inspectionReq.getInspectionReqStatuses()
                .stream()
                .max(Comparator.comparing(InspectionReqStatus::getCreatedDate))
                .orElse(null);
        InspectionReqStatusType inspectionReqStatusType = mostRecentStatus.getInspectionReqStatusType();
        this.businessReqStatusTypeDto = new BusinessReqStatusTypeDto(inspectionReqStatusType);
        this.message = mostRecentStatus.getMessage();
    }

    // Calculate age based on birthday
    private int calculateAge(LocalDate birth) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - birth.getYear();
    }
}
