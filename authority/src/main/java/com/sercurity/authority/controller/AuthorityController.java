package com.sercurity.authority.controller;

import com.sercurity.authority.model.Authority;
import com.sercurity.authority.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    @Autowired
    private AuthorityRepository authorityRepo;

    @GetMapping
    public List<Authority> list() {
        return authorityRepo.findAll();
    }

    @PostMapping
    public Authority create(@RequestBody Authority authority) {
        return authorityRepo.save(authority);
    }

    @PutMapping("/{id}")
    public Authority update(@PathVariable Long id, @RequestBody Authority authority) {
        authority.setId(id);
        return authorityRepo.save(authority);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorityRepo.deleteById(id);
    }
}
