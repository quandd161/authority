package com.sercurity.authority.service;

import com.sercurity.authority.model.Authority;
import com.sercurity.authority.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority save(Authority authority) { return authorityRepository.save(authority); }
    public Optional<Authority> findById(Long id) { return authorityRepository.findById(id); }
    public List<Authority> findAll() { return authorityRepository.findAll(); }
    public void deleteById(Long id) { authorityRepository.deleteById(id); }
}
