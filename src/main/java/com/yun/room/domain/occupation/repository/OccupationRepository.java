package com.yun.room.domain.occupation.repository;

import com.yun.room.domain.occupation.entity.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
}
