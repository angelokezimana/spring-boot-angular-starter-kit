package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.domain.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);

    @Query("select p " +
            " from Permission p " +
            "where p.parent = ?1 ")
    List<Permission> getParent(Permission parent);
}
