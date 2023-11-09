package com.yun.room.domain.component_service.inspection_req.service;

import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req.service.InspectionReqService;
import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;
import com.yun.room.domain.inspection_req_status.service.InspectionReqStatusService;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InspectionReqComponentService {
    private final InspectionReqService inspectionReqService;
    private final RoomService roomService;
    private final MemberService memberService;
    private final InspectionReqStatusService inspectionReqStatusService;
    @Transactional
    public InspectionReq createInspectionReq(
            LocalDateTime inspectionDateTime
            , LocalDate moveInDate
            , Boolean isCurrent
            , Long roomId
            , Long memberId
            , String inspectionReqStatusType) {
        Room room = roomService.findById(roomId);
        Member member = memberService.findById(memberId);
        InspectionReqStatus inspectionReqStatus = inspectionReqStatusService.findByType(inspectionReqStatusType);

        InspectionReq inspectionReq = new InspectionReq(inspectionDateTime, moveInDate, isCurrent, room, member, inspectionReqStatus);
        return inspectionReqService.saveInspectionReq(inspectionReq);
    }
}
