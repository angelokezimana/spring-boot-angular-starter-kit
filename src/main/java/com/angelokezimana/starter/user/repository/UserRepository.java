package com.angelokezimana.starter.user.repository;

import com.angelokezimana.starter.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.permissions WHERE u.email = :username")
    Optional<User> findUserWithRolesAndPermissions(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r WHERE u.email <> 'admin@gmail.com'")
    Page<User> findAllUsers(Pageable pageable);
}
