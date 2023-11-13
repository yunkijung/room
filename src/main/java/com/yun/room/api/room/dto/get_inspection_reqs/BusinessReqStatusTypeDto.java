package com.yun.room.api.room.dto.get_inspection_reqs;

import com.yun.room.domain.inspection_req_status_type.entity.InspectionReqStatusType;
import com.yun.room.domain.inspection_req_status_type.type.ReqStatusType;
import lombok.Data;

@Data
public class BusinessReqStatusTypeDto {
    private Long id;

    private ReqStatusType reqStatusType;
    private Boolean hasHostReject;
    private Boolean hasHostUpdate;
    private Boolean hasHostAccept;
    private Boolean hasHostCancel;
    private Boolean hasHostDelete;

    public BusinessReqStatusTypeDto(InspectionReqStatusType inspectionReqStatusType) {
        this.id = inspectionReqStatusType.getId();
        this.reqStatusType = inspectionReqStatusType.getReqStatusType();
        this.hasHostReject = inspectionReqStatusType.getHasHostReject();
        this.hasHostUpdate = inspectionReqStatusType.getHasHostUpdate();
        this.hasHostAccept = inspectionReqStatusType.getHasHostAccept();
        this.hasHostCancel = inspectionReqStatusType.getHasHostCancel();
        this.hasHostDelete = inspectionReqStatusType.getHasHostDelete();
    }
}
