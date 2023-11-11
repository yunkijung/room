package com.yun.room.domain.inspection_req_status.repository;

import com.yun.room.domain.inspection_req_status.entity.InspectionReqStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionReqStatusRepository extends JpaRepository<InspectionReqStatus, Long> {

}
