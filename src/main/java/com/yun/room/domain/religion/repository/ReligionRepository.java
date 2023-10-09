package com.yun.room.domain.religion.repository;

import com.yun.room.domain.religion.entity.Religion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReligionRepository extends JpaRepository<Religion, Long> {
}
