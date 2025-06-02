package com.sercurity.authority.service;

import com.sercurity.authority.model.Group;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.repository.GroupRepository;
import com.sercurity.authority.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepo;
    private final RoleRepository roleRepo;

    public GroupService(GroupRepository groupRepo, RoleRepository roleRepo) {
        this.groupRepo = groupRepo;
        this.roleRepo = roleRepo;
    }

    public List<Group> getAll() { return groupRepo.findAll(); }
    public Group create(Group g) { return groupRepo.save(g); }
    public void assignRoles(Long groupId, Set<Long> roleIds) {
        Group group = groupRepo.findById(groupId).orElseThrow();
        Set<Role> roles = new HashSet<>(roleRepo.findAllById(roleIds));
        group.setRoles(roles);
        groupRepo.save(group);
    }
    // CRUD khác...
}
