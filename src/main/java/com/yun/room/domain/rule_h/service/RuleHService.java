package com.yun.room.domain.rule_h.service;

import com.yun.room.domain.rule_h.entity.RuleH;
import com.yun.room.domain.rule_h.repository.RuleHRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleHService {
    private final RuleHRepository ruleHRepository;

    @Transactional
    public List<RuleH> findAllByIdIn(List<Long> ids) {
        return ruleHRepository.findAllByIdIn(ids);
    }
}
