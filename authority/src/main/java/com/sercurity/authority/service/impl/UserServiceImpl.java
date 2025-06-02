package com.sercurity.authority.service.impl;

import com.sercurity.authority.model.Group;
import com.sercurity.authority.model.Role;
import com.sercurity.authority.model.User;
import com.sercurity.authority.repository.GroupRepository;
import com.sercurity.authority.repository.RoleRepository;
import com.sercurity.authority.repository.UserRepository;
import com.sercurity.authority.service.JwtService;
import com.sercurity.authority.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {




    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, GroupRepository groupRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<User> getAll() { return userRepository.findAll(); }

    @Override
    public Optional<User> findByUsername(String username) { return userRepository.findByUsername(username); }

    @Override
    public Optional<User> findById(Long id) { return userRepository.findById(id); }

    @Override
    public User saveUser(User user) { return userRepository.save(user); }

    @Override
    public void assginRoles(Long userId, Set<Long> roleIds) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);
        userRepository.save(user);
    }



    @Override
    public void assignGroups(Long userId, Set<Long> groupIds) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Group> groups = new HashSet<>(groupRepository.findAllById(groupIds));
        user.setGroups(groups);
        userRepository.save(user);
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String verify(User user) {
        Authentication authenticate
                = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));
        if(authenticate.isAuthenticated()) {
            return "success";
        }
        return "failure";
    }
}
