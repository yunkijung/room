package com.yun.room.domain.room.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Room extends AuditorEntity {
    @Id
    @Column(name="room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String title;
    private String description;
    private Integer price;
    private Integer minStay;
    private Boolean isOn;
    private LocalDate availableDate;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
}
