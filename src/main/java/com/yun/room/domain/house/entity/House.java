package com.yun.room.domain.house.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.common.embedded.Address;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class House extends AuditorEntity {

    @Id
    @Column(name="house_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;

    private String title;
    private String desc;

    @Embedded
    private Address address;

    private Integer roomCount;
    private Integer washroomCount;
    private Integer toiletCount;
    private Integer kitchenCount;
    private Integer livingRoomCount;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Member host;

    @OneToMany(mappedBy = "house")
    private List<Room> rooms;
}
