package com.sercurity.authority.repository;

import com.sercurity.authority.model.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRoleRepository extends JpaRepository<GroupRole, Long> {
    List<GroupRole> findByGroupId(Long groupId);
    List<GroupRole> findByRoleId(Long roleId);
}
