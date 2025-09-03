package com.sercurity.authority.repository;

import com.sercurity.authority.model.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long> {
    List<RoleAuthority> findByRoleId(Long roleId);
    List<RoleAuthority> findByAuthorityId(Long authorityId);
}
