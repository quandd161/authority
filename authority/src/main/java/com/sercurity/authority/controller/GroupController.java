package com.sercurity.authority.controller;

import com.sercurity.authority.model.Group;
import com.sercurity.authority.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupRepository groupRepo;

    @GetMapping
    public List<Group> list() {
        return groupRepo.findAll();
    }

    @PostMapping
    public Group create(@RequestBody Group group) {
        return groupRepo.save(group);
    }

    @PutMapping("/{id}")
    public Group update(@PathVariable Long id, @RequestBody Group group) {
        group.setId(id);
        return groupRepo.save(group);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupRepo.deleteById(id);
    }
}
