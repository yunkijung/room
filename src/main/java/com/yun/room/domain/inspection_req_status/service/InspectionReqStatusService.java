package com.yun.room.domain.inspection_req_status.service;

import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;
import com.yun.room.domain.inspection_req_status.repository.InspectionReqStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InspectionReqStatusService {
    private final InspectionReqStatusRepository inspectionReqStatusRepository;

    @Transactional
    public InspectionReqStatus findById(Long id) {
        return inspectionReqStatusRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Result."));
    }
    @Transactional
    public InspectionReqStatus findByType(String type) {
        return inspectionReqStatusRepository.findByType(type).orElseThrow(() -> new IllegalArgumentException("No Result."));
    }
}
