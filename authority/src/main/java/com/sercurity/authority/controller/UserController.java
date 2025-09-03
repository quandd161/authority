package com.sercurity.authority.controller;

import com.sercurity.authority.model.User;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.model.Group;
import com.sercurity.authority.service.UserService;
import com.sercurity.authority.dto.UserDto;
import com.sercurity.authority.dto.RoleDto;
import com.sercurity.authority.dto.GroupDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll().stream().map(UserDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.findById(id).map(UserDto::fromEntity).orElse(null);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping
    public UserDto createUser(@RequestBody UserDto req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword()); // Nên mã hóa ở service
        user.setEnabled(req.isEnabled());
        return UserDto.fromEntity(userService.save(user));
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto req) {
        User user = userService.findById(id).orElseThrow();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword()); // Nên mã hóa ở service
        user.setEnabled(req.isEnabled());
        return UserDto.fromEntity(userService.save(user));
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping("/{id}/roles")
    public void assignRoles(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        for (Long rid : roleIds) userService.assignRole(id, rid);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @DeleteMapping("/{id}/roles/{roleId}")
    public void removeRole(@PathVariable Long id, @PathVariable Long roleId) {
        userService.removeRole(id, roleId);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping("/{id}/roles")
    public List<RoleDto> getRoles(@PathVariable Long id) {
        return userService.getRoles(id).stream().map(RoleDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping("/{id}/groups")
    public void assignGroups(@PathVariable Long id, @RequestBody Set<Long> groupIds) {
        for (Long gid : groupIds) userService.assignGroup(id, gid);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @DeleteMapping("/{id}/groups/{groupId}")
    public void removeGroup(@PathVariable Long id, @PathVariable Long groupId) {
        userService.removeGroup(id, groupId);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping("/{id}/groups")
    public List<GroupDto> getGroups(@PathVariable Long id) {
        return userService.getGroups(id).stream().map(GroupDto::fromEntity).toList();
    }
}
