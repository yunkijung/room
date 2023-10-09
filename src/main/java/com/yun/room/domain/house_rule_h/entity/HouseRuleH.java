package com.yun.room.domain.house_rule_h.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.rule_h.entity.RuleH;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HouseRuleH extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseRuleHId;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "rule_h_id")
    private RuleH ruleH;
}
