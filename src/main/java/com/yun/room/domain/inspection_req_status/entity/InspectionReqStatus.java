package com.yun.room.domain.inspection_req_status.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.inspection_req.entity.InspectionReq;

import com.yun.room.domain.inspection_req_status_type.entity.InspectionReqStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class InspectionReqStatus extends AuditorEntity {
    @Id
    @Column(name = "inspection_req_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "inspection_req_status_type_id")
    private InspectionReqStatusType inspectionReqStatusType;
    private String message;

    @ManyToOne
    @JoinColumn(name = "inspection_req_id")
    private InspectionReq inspectionReq;

    public InspectionReqStatus(InspectionReqStatusType inspectionReqStatusType, String message, InspectionReq inspectionReq) {
        this.inspectionReqStatusType = inspectionReqStatusType;
        this.message = message;
        this.inspectionReq = inspectionReq;
    }
}
