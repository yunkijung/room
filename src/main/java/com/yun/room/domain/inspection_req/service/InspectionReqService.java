package com.yun.room.domain.inspection_req.service;

import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req.repository.InspectionReqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionReqService {
    private final InspectionReqRepository inspectionReqRepository;
    @Transactional
    public InspectionReq saveInspectionReq(InspectionReq inspectionReq) {
        return inspectionReqRepository.save(inspectionReq);
    }

    @Transactional
    public List<InspectionReq> findByMemberId(Long id) {
        return inspectionReqRepository.findByMember_id(id);
    }

    @Transactional
    public List<InspectionReq> findByRoomId(Long id) {
        return inspectionReqRepository.findByRoom_id(id);
    }
}
