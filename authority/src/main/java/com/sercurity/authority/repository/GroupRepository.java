package com.sercurity.authority.repository;

import com.sercurity.authority.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
