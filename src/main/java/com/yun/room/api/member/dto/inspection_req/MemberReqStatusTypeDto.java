package com.yun.room.api.member.dto.inspection_req;

import com.yun.room.domain.inspection_req_status_type.entity.InspectionReqStatusType;
import com.yun.room.domain.inspection_req_status_type.type.ReqStatusType;
import lombok.Data;

@Data
public class MemberReqStatusTypeDto {
    private Long id;

    private ReqStatusType reqStatusType;
    private Boolean hasTenantCancel;
    private Boolean hasTenantReject;
    private Boolean hasTenantUpdate;
    private Boolean hasTenantConfirm;
    private Boolean hasTenantDelete;

    public MemberReqStatusTypeDto(InspectionReqStatusType inspectionReqStatusType) {
        this.id = inspectionReqStatusType.getId();
        this.reqStatusType = inspectionReqStatusType.getReqStatusType();
        this.hasTenantCancel = inspectionReqStatusType.getHasTenantCancel();
        this.hasTenantReject = inspectionReqStatusType.getHasTenantReject();
        this.hasTenantUpdate = inspectionReqStatusType.getHasTenantUpdate();
        this.hasTenantConfirm = inspectionReqStatusType.getHasTenantConfirm();
        this.hasTenantDelete = inspectionReqStatusType.getHasTenantDelete();
    }
}
