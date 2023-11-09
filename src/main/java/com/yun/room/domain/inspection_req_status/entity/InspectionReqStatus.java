package com.yun.room.domain.inspection_req_status.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class InspectionReqStatus {
    @Id
    @Column(name = "inspection_req_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public InspectionReqStatus(String type) {
        this.type = type;
    }
}
