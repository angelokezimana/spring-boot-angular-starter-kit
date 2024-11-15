package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value="SELECT u FROM User u " +
            "LEFT JOIN FETCH u.posts " +
            "LEFT JOIN FETCH u.comments " +
            "LEFT JOIN FETCH u.roles " +
            "WHERE u.email = :email")
    Optional<User> findByEmailWithAssociations(String email);
}
