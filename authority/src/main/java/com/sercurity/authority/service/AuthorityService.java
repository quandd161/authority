package com.sercurity.authority.service;

import com.sercurity.authority.model.Authority;
import com.sercurity.authority.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepo;
    public AuthorityService(AuthorityRepository authorityRepo) { this.authorityRepo = authorityRepo; }
    public List<Authority> getAll() { return authorityRepo.findAll(); }
    public Authority create(Authority a) { return authorityRepo.save(a); }
    // CRUD khác...
}
