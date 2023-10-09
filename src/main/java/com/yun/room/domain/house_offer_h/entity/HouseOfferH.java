package com.yun.room.domain.house_offer_h.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.rule_h.entity.RuleH;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HouseOfferH extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseOfferHId;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "offer_h_id")
    private OfferH offerH;
}
