package com.yun.room.domain.room_offer_r.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomOfferR extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomOfferRId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "offer_r_id")
    private OfferR offerR;
}
