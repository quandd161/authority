package com.sercurity.authority.service;

import com.sercurity.authority.model.User;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.model.Authority;
import com.sercurity.authority.model.Group;
import com.sercurity.authority.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final GroupService groupService;
    private final RoleService roleService;

    public CustomUserDetailsService(
            UserRepository userRepository,
            UserService userService,
            GroupService groupService,
            RoleService roleService
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.groupService = groupService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        // Lấy roles trực tiếp từ user
        Set<Long> roleIds = new HashSet<>();
        List<Role> directRoles = userService.getRoles(user.getId());
        for (Role r : directRoles) roleIds.add(r.getId());

        // Lấy roles gián tiếp từ group của user
        List<Group> groups = userService.getGroups(user.getId());
        for (Group g : groups) {
            List<Role> groupRoles = groupService.getRoles(g.getId());
            for (Role r : groupRoles) roleIds.add(r.getId());
        }

        // Lấy authorities từ các role (bao gồm cả tên role: ROLE_xxx và authority thực)
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleService.findById(roleId).orElse(null);
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                List<Authority> roleAuths = roleService.getAuthorities(roleId);
                for (Authority a : roleAuths) {
                    authorities.add(new SimpleGrantedAuthority(a.getName()));
                }
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true,
                true, true, true, authorities
        );
    }
}