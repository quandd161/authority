package com.sercurity.authority.service;

import com.sercurity.authority.model.Group;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.repository.GroupRepository;
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
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final RoleRepository roleRepository;
    private final GroupUserRepository groupUserRepository;
    private final UserRepository userRepository;

    public GroupService(
            GroupRepository groupRepository,
            GroupRoleRepository groupRoleRepository,
            RoleRepository roleRepository,
            GroupUserRepository groupUserRepository,
            UserRepository userRepository
    ) {
        this.groupRepository = groupRepository;
        this.groupRoleRepository = groupRoleRepository;
        this.roleRepository = roleRepository;
        this.groupUserRepository = groupUserRepository;
        this.userRepository = userRepository;
    }

    // CRUD
    public Group save(Group group) { return groupRepository.save(group); }
    public Optional<Group> findById(Long id) { return groupRepository.findById(id); }
    public List<Group> findAll() { return groupRepository.findAll(); }
    public void deleteById(Long id) { groupRepository.deleteById(id); }

    // Assign Role
    public void assignRole(Long groupId, Long roleId) {
        if (groupRoleRepository.findByGroupId(groupId).stream().noneMatch(gr -> gr.getRoleId().equals(roleId))) {
            groupRoleRepository.save(new GroupRole(null, groupId, roleId));
        }
    }
    // Remove Role
    public void removeRole(Long groupId, Long roleId) {
        groupRoleRepository.findByGroupId(groupId)
                .stream().filter(gr -> gr.getRoleId().equals(roleId))
                .forEach(gr -> groupRoleRepository.deleteById(gr.getId()));
    }
    // Get Roles
    public List<Role> getRoles(Long groupId) {
        List<Long> roleIds = groupRoleRepository.findByGroupId(groupId)
                .stream().map(GroupRole::getRoleId).collect(Collectors.toList());
        return roleRepository.findAllById(roleIds);
    }

    // Assign User
    public void assignUser(Long groupId, Long userId) {
        if (groupUserRepository.findByGroupId(groupId).stream().noneMatch(gu -> gu.getUserId().equals(userId))) {
            groupUserRepository.save(new GroupUser(null, userId, groupId));
        }
    }
    // Remove User
    public void removeUser(Long groupId, Long userId) {
        groupUserRepository.findByGroupId(groupId)
                .stream().filter(gu -> gu.getUserId().equals(userId))
                .forEach(gu -> groupUserRepository.deleteById(gu.getId()));
    }
    // Get Users
    public List<User> getUsers(Long groupId) {
        List<Long> userIds = groupUserRepository.findByGroupId(groupId)
                .stream().map(GroupUser::getUserId).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }
}
