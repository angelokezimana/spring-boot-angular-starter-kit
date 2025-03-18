package com.angelokezimana.starter.repository.security;

import com.angelokezimana.starter.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findByNameContainingIgnoreCase(String name);
}
