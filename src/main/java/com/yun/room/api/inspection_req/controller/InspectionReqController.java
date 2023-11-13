package com.yun.room.api.inspection_req.controller;

import com.yun.room.api.inspection_req.dto.create_inspection_req.InspectionReqForm;

import com.yun.room.api.inspection_req.dto.create_inspection_req_status.InspectionReqStatusForm;
import com.yun.room.domain.component_service.inspection_req.service.InspectionReqComponentService;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/inspection-req")
public class InspectionReqController {
    private final InspectionReqComponentService inspectionReqComponentService;
    @PostMapping
    public ResponseEntity createInspectionReq(@IfLogin LoginUserDto loginUserDto, @RequestBody @Valid InspectionReqForm inspectionReqForm, BindingResult bindingResult) {
        log.info("time: {}", inspectionReqForm.getInspectionDateTime());
        inspectionReqComponentService.createInspectionReq(inspectionReqForm.getInspectionDateTime(), inspectionReqForm.getMoveInDate(), false, false, inspectionReqForm.getRoomId(), loginUserDto.getMemberId(), inspectionReqForm.getReqStatusType(), inspectionReqForm.getMessage());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity createInspectionReqStatus(@RequestBody @Valid InspectionReqStatusForm inspectionReqStatusForm, BindingResult bindingResult) {

        inspectionReqComponentService.createInspectionReqStatus(
                inspectionReqStatusForm.getInspectionReqId()
                , inspectionReqStatusForm.getInspectionDateTime()
                , inspectionReqStatusForm.getReqStatusType()
                , inspectionReqStatusForm.getMessage()
                );

        return new ResponseEntity(HttpStatus.OK);
    }


}
