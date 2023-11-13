package com.yun.room.domain.inspection_req_status_type.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.inspection_req_status_type.type.ReqStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class InspectionReqStatusType extends AuditorEntity {
    @Id
    @Column(name = "inspection_req_status_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReqStatusType reqStatusType;
    private Boolean hasHostReject;
    private Boolean hasHostUpdate;
    private Boolean hasHostAccept;
    private Boolean hasHostCancel;
    private Boolean hasHostDelete;

    private Boolean hasTenantCancel;
    private Boolean hasTenantReject;
    private Boolean hasTenantUpdate;
    private Boolean hasTenantConfirm;
    private Boolean hasTenantDelete;

    public InspectionReqStatusType(ReqStatusType reqStatusType, Boolean hasHostReject, Boolean hasHostUpdate, Boolean hasHostAccept, Boolean hasHostCancel, Boolean hasHostDelete, Boolean hasTenantCancel, Boolean hasTenantReject, Boolean hasTenantUpdate, Boolean hasTenantConfirm, Boolean hasTenantDelete) {
        this.reqStatusType = reqStatusType;
        this.hasHostReject = hasHostReject;
        this.hasHostUpdate = hasHostUpdate;
        this.hasHostAccept = hasHostAccept;
        this.hasHostCancel = hasHostCancel;
        this.hasHostDelete = hasHostDelete;
        this.hasTenantCancel = hasTenantCancel;
        this.hasTenantReject = hasTenantReject;
        this.hasTenantUpdate = hasTenantUpdate;
        this.hasTenantConfirm = hasTenantConfirm;
        this.hasTenantDelete = hasTenantDelete;
    }
}
