package com.yun.room.domain.rule_h.repository;

import com.yun.room.domain.rule_h.entity.RuleH;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleHRepository extends JpaRepository<RuleH, Long> {
    List<RuleH> findAllByIdIn(List<Long> ids);
}
