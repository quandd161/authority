package com.sercurity.authority.repository;

import com.sercurity.authority.model.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    List<GroupUser> findByGroupId(Long groupId);
    List<GroupUser> findByUserId(Long userId);
}
