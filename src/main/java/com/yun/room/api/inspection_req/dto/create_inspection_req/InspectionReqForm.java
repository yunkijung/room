package com.yun.room.api.inspection_req.dto.create_inspection_req;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InspectionReqForm {
    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Long roomId;
}
