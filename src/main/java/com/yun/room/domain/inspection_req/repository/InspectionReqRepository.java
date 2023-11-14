package com.yun.room.domain.inspection_req.repository;

import com.yun.room.domain.inspection_req.entity.InspectionReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InspectionReqRepository extends JpaRepository<InspectionReq, Long> {
    @Query("select i from InspectionReq i where i.member.id = :id AND i.isDeletedByTenant = false")
    List<InspectionReq> findByMember_id(@Param("id") Long id);
    @Query("select i from InspectionReq i where i.room.id = :id AND i.isDeletedByHost = false")
    List<InspectionReq> findByRoom_id(@Param("id") Long id);
}
