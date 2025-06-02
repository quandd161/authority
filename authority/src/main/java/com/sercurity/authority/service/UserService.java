package com.sercurity.authority.service;

import com.sercurity.authority.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    User register(User user);

    String verify(User user);

    List<User> getAll();

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    User saveUser(User user);

    void assginRoles(Long userId, Set<Long> roleIds);

    void assignGroups(Long userId, Set<Long> groupIds);
}
