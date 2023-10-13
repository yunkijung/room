package com.yun.room.domain.contract.entity;

import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Contract {
    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Member host;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Member tenant;
}
