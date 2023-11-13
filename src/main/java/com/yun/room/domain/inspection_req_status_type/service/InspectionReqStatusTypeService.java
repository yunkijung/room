package com.yun.room.domain.inspection_req_status_type.service;

import com.yun.room.domain.inspection_req_status_type.entity.InspectionReqStatusType;
import com.yun.room.domain.inspection_req_status_type.repository.InspectionReqStatusTypeRepository;
import com.yun.room.domain.inspection_req_status_type.type.ReqStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InspectionReqStatusTypeService {
    private final InspectionReqStatusTypeRepository inspectionReqStatusTypeRepository;

    @Transactional
    public InspectionReqStatusType findByReqStatusType(ReqStatusType reqStatusType) {
        return inspectionReqStatusTypeRepository.findByReqStatusType(reqStatusType).orElseThrow(() -> new IllegalArgumentException("Not exist."));
    }
}
