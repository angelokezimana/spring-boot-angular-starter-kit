package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
