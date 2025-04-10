package com.angelokezimana.starter.role.repository;

import com.angelokezimana.starter.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions p WHERE lower(r.name) LIKE lower(concat('%', :name, '%'))")
    List<Role> findByNameContainingIgnoreCase(@Param("name") String name);
}
