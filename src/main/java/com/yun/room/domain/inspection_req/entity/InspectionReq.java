package com.yun.room.domain.inspection_req.entity;

import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class InspectionReq {
    @Id
    @Column(name = "inspection_req_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inspectionDateTime;
    private LocalDate moveInDate;
    private Boolean isCurrent;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Member member;

    @OneToMany(mappedBy = "inspectionReq", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<InspectionReqStatus> inspectionReqStatuses;

    public InspectionReq(LocalDateTime inspectionDateTime, LocalDate moveInDate, Boolean isCurrent, Room room, Member member) {
        this.inspectionDateTime = inspectionDateTime;
        this.moveInDate = moveInDate;
        this.isCurrent = isCurrent;
        this.room = room;
        this.member = member;
    }

    public void updateInspectionReqStatuses(List<InspectionReqStatus> inspectionReqStatuses) {
        this.inspectionReqStatuses = inspectionReqStatuses;
    }

    public void updateInspectionDateTime(LocalDateTime inspectionDateTime) {
        this.inspectionDateTime = inspectionDateTime;
    }
}
