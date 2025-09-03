package com.sercurity.authority.controller;

import com.sercurity.authority.model.Group;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.model.User;
import com.sercurity.authority.service.GroupService;
import com.sercurity.authority.dto.GroupDto;
import com.sercurity.authority.dto.RoleDto;
import com.sercurity.authority.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    public GroupController(GroupService groupService) { this.groupService = groupService; }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @GetMapping
    public List<GroupDto> getAll() {
        return groupService.findAll().stream().map(GroupDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable Long id) {
        return groupService.findById(id).map(GroupDto::fromEntity).orElse(null);
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @PostMapping
    public GroupDto create(@RequestBody GroupDto dto) {
        Group g = new Group();
        g.setName(dto.getName());
        return GroupDto.fromEntity(groupService.save(g));
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @PutMapping("/{id}")
    public GroupDto update(@PathVariable Long id, @RequestBody GroupDto dto) {
        Group g = groupService.findById(id).orElseThrow();
        g.setName(dto.getName());
        return GroupDto.fromEntity(groupService.save(g));
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @PostMapping("/{id}/roles")
    public void assignRoles(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        for (Long rid : roleIds) groupService.assignRole(id, rid);
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @DeleteMapping("/{id}/roles/{roleId}")
    public void removeRole(@PathVariable Long id, @PathVariable Long roleId) {
        groupService.removeRole(id, roleId);
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @GetMapping("/{id}/roles")
    public List<RoleDto> getRoles(@PathVariable Long id) {
        return groupService.getRoles(id).stream().map(RoleDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @PostMapping("/{id}/users")
    public void assignUsers(@PathVariable Long id, @RequestBody Set<Long> userIds) {
        for (Long uid : userIds) groupService.assignUser(id, uid);
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @DeleteMapping("/{id}/users/{userId}")
    public void removeUser(@PathVariable Long id, @PathVariable Long userId) {
        groupService.removeUser(id, userId);
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @GetMapping("/{id}/users")
    public List<UserDto> getUsers(@PathVariable Long id) {
        return groupService.getUsers(id).stream().map(UserDto::fromEntity).toList();
    }
}
