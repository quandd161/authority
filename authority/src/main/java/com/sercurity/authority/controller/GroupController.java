package com.sercurity.authority.controller;

import com.sercurity.authority.dto.GroupDto;
import com.sercurity.authority.model.Group;
import com.sercurity.authority.repository.GroupRepository;
import com.sercurity.authority.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return groupService.getAll().stream().map(GroupDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @PostMapping
    public GroupDto create(@RequestBody GroupDto dto) {
        Group g = new Group();
        g.setName(dto.getName());
        return GroupDto.fromEntity(groupService.create(g));
    }

    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    @PostMapping("/{id}/roles")
    public void assignRoles(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        groupService.assignRoles(id, roleIds);
    }
}
