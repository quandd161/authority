package com.sercurity.authority.controller;

import com.sercurity.authority.model.Authority;
import com.sercurity.authority.service.AuthorityService;
import com.sercurity.authority.dto.AuthorityDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    private final AuthorityService authorityService;
    public AuthorityController(AuthorityService authorityService) { this.authorityService = authorityService; }

    @PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
    @GetMapping
    public List<AuthorityDto> getAll() {
        return authorityService.findAll().stream().map(AuthorityDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
    @GetMapping("/{id}")
    public AuthorityDto getById(@PathVariable Long id) {
        return authorityService.findById(id).map(AuthorityDto::fromEntity).orElse(null);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
    @PostMapping
    public AuthorityDto create(@RequestBody AuthorityDto dto) {
        Authority a = new Authority();
        a.setName(dto.getName());
        return AuthorityDto.fromEntity(authorityService.save(a));
    }

    @PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
    @PutMapping("/{id}")
    public AuthorityDto update(@PathVariable Long id, @RequestBody AuthorityDto dto) {
        Authority a = authorityService.findById(id).orElseThrow();
        a.setName(dto.getName());
        return AuthorityDto.fromEntity(authorityService.save(a));
    }

    @PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorityService.deleteById(id);
    }
}
