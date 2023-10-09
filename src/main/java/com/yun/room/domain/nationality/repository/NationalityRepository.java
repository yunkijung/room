package com.yun.room.domain.nationality.repository;

import com.yun.room.domain.nationality.entity.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalityRepository extends JpaRepository<Nationality, Long> {
}
