package com.yun.room.api.house.dto.get_options;

import com.yun.room.domain.rule_h.entity.RuleH;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleHDto {
    private Long ruleHId;
    private String type;
    private String description;

    public RuleHDto(RuleH ruleH) {
        this.ruleHId = ruleH.getId();
        this.type = ruleH.getType();
        this.description = ruleH.getDescription();
    }
}
