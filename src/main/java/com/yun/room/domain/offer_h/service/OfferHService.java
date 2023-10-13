package com.yun.room.domain.offer_h.service;

import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.offer_h.repository.OfferHRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferHService {
    private final OfferHRepository offerHRepository;

    @Transactional
    public List<OfferH> findAllByIdIn(List<Long> ids) {
        return offerHRepository.findAllByIdIn(ids);
    }
}
