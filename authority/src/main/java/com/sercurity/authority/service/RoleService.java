package com.sercurity.authority.service;

import com.sercurity.authority.model.Authority;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.repository.AuthorityRepository;
import com.sercurity.authority.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepo;
    private final AuthorityRepository authorityRepo;

    public RoleService(RoleRepository roleRepo, AuthorityRepository authorityRepo) {
        this.roleRepo = roleRepo;
        this.authorityRepo = authorityRepo;
    }

    public List<Role> getAll() { return roleRepo.findAll(); }
    public Role create(Role r) { return roleRepo.save(r); }
    public void assignAuthorities(Long roleId, Set<Long> authorityIds) {
        Role role = roleRepo.findById(roleId).orElseThrow();
        Set<Authority> authorities = new HashSet<>(authorityRepo.findAllById(authorityIds));
        role.setAuthorities(authorities);
        roleRepo.save(role);
    }
    // CRUD khác...
}
