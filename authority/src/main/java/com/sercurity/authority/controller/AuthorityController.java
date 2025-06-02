package com.sercurity.authority.controller;

import com.sercurity.authority.dto.AuthorityDto;
import com.sercurity.authority.model.Authority;
import com.sercurity.authority.repository.AuthorityRepository;
import com.sercurity.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return authorityService.getAll().stream().map(AuthorityDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
    @PostMapping
    public AuthorityDto create(@RequestBody AuthorityDto dto) {
        Authority a = new Authority();
        a.setName(dto.getName());
        return AuthorityDto.fromEntity(authorityService.create(a));
    }
}
