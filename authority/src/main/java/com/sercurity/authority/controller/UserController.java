package com.sercurity.authority.controller;

import com.sercurity.authority.dto.UserDto;
import com.sercurity.authority.model.User;
import com.sercurity.authority.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll().stream().map(UserDto::fromEntity).toList();
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping
    public UserDto createUser(@RequestBody UserDto req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword()); // nên mã hóa ở service
        user.setEnabled(req.isEnabled());
        return UserDto.fromEntity(userService.saveUser(user));
    }


    // ... các API khác (CRUD, assign role/group, ...)
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping("/{id}/roles")
    public void assignRoles(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        userService.assginRoles(id, roleIds);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping("/{id}/groups")
    public void assignGroups(@PathVariable Long id, @RequestBody Set<Long> groupIds) {
        userService.assignGroups(id, groupIds);
    }
}
