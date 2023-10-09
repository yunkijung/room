package com.yun.room.domain.gender.repository;

import com.yun.room.domain.gender.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}
