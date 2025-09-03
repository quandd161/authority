package com.sercurity.authority.service;

import com.sercurity.authority.model.Authority;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.repository.AuthorityRepository;
import com.sercurity.authority.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sercurity.authority.model.*;
import com.sercurity.authority.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final GroupRepository groupRepository;

    public RoleService(
            RoleRepository roleRepository,
            RoleAuthorityRepository roleAuthorityRepository,
            AuthorityRepository authorityRepository,
            GroupRoleRepository groupRoleRepository,
            GroupRepository groupRepository
    ) {
        this.roleRepository = roleRepository;
        this.roleAuthorityRepository = roleAuthorityRepository;
        this.authorityRepository = authorityRepository;
        this.groupRoleRepository = groupRoleRepository;
        this.groupRepository = groupRepository;
    }

    // CRUD
    public Role save(Role role) { return roleRepository.save(role); }
    public Optional<Role> findById(Long id) { return roleRepository.findById(id); }
    public List<Role> findAll() { return roleRepository.findAll(); }
    public void deleteById(Long id) { roleRepository.deleteById(id); }

    // Assign Authority
    public void assignAuthority(Long roleId, Long authorityId) {
        if (roleAuthorityRepository.findByRoleId(roleId).stream().noneMatch(ra -> ra.getAuthorityId().equals(authorityId))) {
            roleAuthorityRepository.save(new RoleAuthority(null, roleId, authorityId));
        }
    }
    // Remove Authority
    public void removeAuthority(Long roleId, Long authorityId) {
        roleAuthorityRepository.findByRoleId(roleId)
                .stream().filter(ra -> ra.getAuthorityId().equals(authorityId))
                .forEach(ra -> roleAuthorityRepository.deleteById(ra.getId()));
    }
    // Get Authorities
    public List<Authority> getAuthorities(Long roleId) {
        List<Long> authorityIds = roleAuthorityRepository.findByRoleId(roleId)
                .stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        return authorityRepository.findAllById(authorityIds);
    }

    // Assign Group
    public void assignGroup(Long roleId, Long groupId) {
        if (groupRoleRepository.findByRoleId(roleId).stream().noneMatch(gr -> gr.getGroupId().equals(groupId))) {
            groupRoleRepository.save(new GroupRole(null, groupId, roleId));
        }
    }
    // Remove Group
    public void removeGroup(Long roleId, Long groupId) {
        groupRoleRepository.findByRoleId(roleId)
                .stream().filter(gr -> gr.getGroupId().equals(groupId))
                .forEach(gr -> groupRoleRepository.deleteById(gr.getId()));
    }
    // Get Groups
    public List<Group> getGroups(Long roleId) {
        List<Long> groupIds = groupRoleRepository.findByRoleId(roleId)
                .stream().map(GroupRole::getGroupId).collect(Collectors.toList());
        return groupRepository.findAllById(groupIds);
    }
}
