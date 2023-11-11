package com.yun.room.domain.component_service.inspection_req.service;

import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req.service.InspectionReqService;
import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;
import com.yun.room.domain.inspection_req_status.service.InspectionReqStatusService;
import com.yun.room.domain.inspection_req_status.type.InspectionReqStatusType;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionReqComponentService {
    private final InspectionReqService inspectionReqService;
    private final RoomService roomService;
    private final MemberService memberService;
    @Transactional
    public InspectionReq createInspectionReq(
            LocalDateTime inspectionDateTime
            , LocalDate moveInDate
            , Boolean isCurrent
            , Long roomId
            , Long memberId
            , InspectionReqStatusType inspectionReqStatusType
            , String message) {
        Room room = roomService.findById(roomId);
        Member member = memberService.findById(memberId);

        InspectionReq inspectionReq = new InspectionReq(inspectionDateTime, moveInDate, isCurrent, room, member);
        List<InspectionReqStatus> inspectionReqStatuses = new ArrayList<>();
        inspectionReqStatuses.add(new InspectionReqStatus(inspectionReqStatusType, message, inspectionReq));

        inspectionReq.updateInspectionReqStatuses(inspectionReqStatuses);

        return inspectionReqService.saveInspectionReq(inspectionReq);
    }

    @Transactional
    public InspectionReq createInspectionReqStatus(
            Long inspectionReqId
            , LocalDateTime inspectionDateTime
            , InspectionReqStatusType inspectionReqStatusType
            , String message) {
        InspectionReq inspectionReq = inspectionReqService.findById(inspectionReqId);

        if(inspectionDateTime != null) {
            inspectionReq.updateInspectionDateTime(inspectionDateTime);
        }

        inspectionReq.getInspectionReqStatuses().add(new InspectionReqStatus(inspectionReqStatusType, message, inspectionReq));

        return inspectionReq;
    }


}
