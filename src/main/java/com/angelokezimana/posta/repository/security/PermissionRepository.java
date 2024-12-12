package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.entity.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByResourceAndAction(String resource, String action);
    List<Permission> findByResource(String resource);
}
