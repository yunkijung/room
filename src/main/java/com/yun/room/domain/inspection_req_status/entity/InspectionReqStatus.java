package com.yun.room.domain.inspection_req_status.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class InspectionReqStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inspectionReqStatusId;

    private String type;
}
