package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
