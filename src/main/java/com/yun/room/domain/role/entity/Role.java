package com.yun.room.domain.role.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
@NoArgsConstructor
@Setter
@Getter
public class Role extends AuditorEntity {
    @Id
    @Column(name="role_id")
    private Long roleId;

    @Column(length = 20)
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}