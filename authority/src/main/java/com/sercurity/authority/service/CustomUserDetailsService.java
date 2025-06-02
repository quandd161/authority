package com.sercurity.authority.service;

import com.sercurity.authority.CustomUserDetails;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.model.User;
import com.sercurity.authority.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Set<Role> allRoles = new HashSet<>(user.getRoles());
        if (user.getGroups() != null) user.getGroups().forEach(g -> allRoles.addAll(g.getRoles()));
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Role role : allRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            if (role.getAuthorities() != null)
                role.getAuthorities().forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getName())));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }
}
