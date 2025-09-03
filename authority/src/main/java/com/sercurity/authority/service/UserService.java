package com.sercurity.authority.service;

import com.sercurity.authority.model.*;
import com.sercurity.authority.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final GroupUserRepository groupUserRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;

    public UserService(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            GroupUserRepository groupUserRepository,
            RoleRepository roleRepository,
            GroupRepository groupRepository
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.groupUserRepository = groupUserRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    // CRUD
    public User save(User user) { return userRepository.save(user); }
    public Optional<User> findById(Long id) { return userRepository.findById(id); }
    public List<User> findAll() { return userRepository.findAll(); }
    public void deleteById(Long id) { userRepository.deleteById(id); }

    // Assign Role
    public void assignRole(Long userId, Long roleId) {
        if (userRoleRepository.findByUserId(userId).stream().noneMatch(ur -> ur.getRoleId().equals(roleId))) {
            userRoleRepository.save(new UserRole(null, userId, roleId));
        }
    }
    // Remove Role
    public void removeRole(Long userId, Long roleId) {
        userRoleRepository.findByUserId(userId)
                .stream().filter(ur -> ur.getRoleId().equals(roleId))
                .forEach(ur -> userRoleRepository.deleteById(ur.getId()));
    }
    // Get Roles
    public List<Role> getRoles(Long userId) {
        List<Long> roleIds = userRoleRepository.findByUserId(userId)
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return roleRepository.findAllById(roleIds);
    }

    // Assign Group
    public void assignGroup(Long userId, Long groupId) {
        if (groupUserRepository.findByUserId(userId).stream().noneMatch(gu -> gu.getGroupId().equals(groupId))) {
            groupUserRepository.save(new GroupUser(null, userId, groupId));
        }
    }
    // Remove Group
    public void removeGroup(Long userId, Long groupId) {
        groupUserRepository.findByUserId(userId)
                .stream().filter(gu -> gu.getGroupId().equals(groupId))
                .forEach(gu -> groupUserRepository.deleteById(gu.getId()));
    }
    // Get Groups
    public List<Group> getGroups(Long userId) {
        List<Long> groupIds = groupUserRepository.findByUserId(userId)
                .stream().map(GroupUser::getGroupId).collect(Collectors.toList());
        return groupRepository.findAllById(groupIds);
    }
}
