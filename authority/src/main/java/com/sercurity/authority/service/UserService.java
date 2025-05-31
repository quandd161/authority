package com.sercurity.authority.service;

import com.sercurity.authority.model.User;

public interface UserService {

    User register(User user);

    String verify(User user);
}
