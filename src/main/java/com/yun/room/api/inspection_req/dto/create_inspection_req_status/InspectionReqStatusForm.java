package com.yun.room.api.inspection_req.dto.create_inspection_req_status;

import com.yun.room.domain.inspection_req_status.type.InspectionReqStatusType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InspectionReqStatusForm {
    private Long inspectionReqId;
    private LocalDateTime inspectionDateTime;
    private InspectionReqStatusType inspectionReqStatusType;
    private String message;
}
