package com.yun.room.domain.role.repository;

import com.yun.room.domain.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : urstory
 * @mailto : urstory@peonani.com
 * @created : 2023/02/15, 수요일
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
