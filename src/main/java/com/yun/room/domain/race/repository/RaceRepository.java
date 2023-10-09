package com.yun.room.domain.race.repository;

import com.yun.room.domain.race.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
}
