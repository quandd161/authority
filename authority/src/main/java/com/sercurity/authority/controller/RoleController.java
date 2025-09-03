package com.sercurity.authority.controller;

import com.sercurity.authority.model.Role;
import com.sercurity.authority.service.RoleService;
import com.sercurity.authority.model.Authority;
import com.sercurity.authority.dto.RoleDto;
import com.sercurity.authority.dto.AuthorityDto;
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
        return roleService.findAll().stream().map(RoleDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable Long id) {
        return roleService.findById(id).map(RoleDto::fromEntity).orElse(null);
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @PostMapping
    public RoleDto create(@RequestBody RoleDto dto) {
        Role r = new Role();
        r.setName(dto.getName());
        return RoleDto.fromEntity(roleService.save(r));
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @PutMapping("/{id}")
    public RoleDto update(@PathVariable Long id, @RequestBody RoleDto dto) {
        Role r = roleService.findById(id).orElseThrow();
        r.setName(dto.getName());
        return RoleDto.fromEntity(roleService.save(r));
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @PostMapping("/{id}/authorities")
    public void assignAuthorities(@PathVariable Long id, @RequestBody Set<Long> authorityIds) {
        authorityIds.forEach(aid -> roleService.assignAuthority(id, aid));
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @DeleteMapping("/{id}/authorities/{authorityId}")
    public void removeAuthority(@PathVariable Long id, @PathVariable Long authorityId) {
        roleService.removeAuthority(id, authorityId);
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    @GetMapping("/{id}/authorities")
    public List<AuthorityDto> getAuthorities(@PathVariable Long id) {
        return roleService.getAuthorities(id).stream().map(AuthorityDto::fromEntity).toList();
    }
}
