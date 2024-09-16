package com.angelokezimana.posta.service.security;

import com.angelokezimana.posta.entity.security.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getCurrentUser();
}
