package com.yun.room.domain.role.service;

import com.yun.room.domain.role.entity.Role;
import com.yun.room.domain.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("해당 권한이 없습니다."));
    }
}
