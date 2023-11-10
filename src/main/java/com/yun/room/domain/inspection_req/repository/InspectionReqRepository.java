package com.yun.room.domain.inspection_req.repository;

import com.yun.room.domain.inspection_req.entity.InspectionReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionReqRepository extends JpaRepository<InspectionReq, Long> {
    List<InspectionReq> findByMember_id(Long id);

    List<InspectionReq> findByRoom_id(Long id);
}
