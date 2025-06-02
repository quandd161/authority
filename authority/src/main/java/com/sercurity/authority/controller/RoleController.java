package com.sercurity.authority.controller;

import com.sercurity.authority.dto.RoleDto;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.repository.RoleRepository;
import com.sercurity.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) { this.roleService = roleService; }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll().stream().map(RoleDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @PostMapping
    public RoleDto create(@RequestBody RoleDto dto) {
        Role r = new Role();
        r.setName(dto.getName());
        return RoleDto.fromEntity(roleService.create(r));
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @PostMapping("/{id}/authorities")
    public void assignAuthorities(@PathVariable Long id, @RequestBody Set<Long> authorityIds) {
        roleService.assignAuthorities(id, authorityIds);
    }
}
