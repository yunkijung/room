package com.yun.room.api.room.dto.get_inspection_reqs;

import com.yun.room.api.house.dto.search_houses.RoomDto;
import com.yun.room.domain.inspection_req.entity.InspectionReq;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BusinessInspectionReqResponseDto {
    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Boolean isCurrent;
    private RoomDto room;
    private TenantInfoDto tenant;
    private String inspectionReqStatusType;

    public BusinessInspectionReqResponseDto(InspectionReq inspectionReq) {
        this.inspectionDateTime = inspectionReq.getInspectionDateTime();
        this.moveInDate = inspectionReq.getMoveInDate();
        this.isCurrent = inspectionReq.getIsCurrent();
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
        this.inspectionReqStatusType = inspectionReq.getInspectionReqStatus().getType();
    }

    // Calculate age based on birthday
    private int calculateAge(LocalDate birth) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - birth.getYear();
    }
}
