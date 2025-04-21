package com.angelokezimana.starter;


import com.angelokezimana.starter.admin.model.Permission;
import com.angelokezimana.starter.admin.model.Role;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.admin.repository.PermissionRepository;
import com.angelokezimana.starter.admin.repository.RoleRepository;
import com.angelokezimana.starter.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class DataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository,
                      RoleRepository roleRepository,
                      PermissionRepository permissionRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        List<String> actions = Arrays.asList("CREATE", "READ", "UPDATE", "DELETE");

        List<Permission> rolePermission = createPermissionIfNotFound("ROLE", actions);
        List<Permission> userPermission = createPermissionIfNotFound("USER", actions);
        List<Permission> postPermission = createPermissionIfNotFound("POST", actions);
        List<Permission> commentPermission = createPermissionIfNotFound("COMMENT", actions);

        Set<Permission> adminPrivileges = new HashSet<>();

        adminPrivileges.addAll(rolePermission);
        adminPrivileges.addAll(userPermission);
        adminPrivileges.addAll(postPermission);
        adminPrivileges.addAll(commentPermission);

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", new HashSet<>());

        createAdminIfNotFound(adminRole);

        alreadySetup = true;
    }

    @Transactional
    List<Permission> createPermissionIfNotFound(String resource, List<String> actions) {

        for (String action : actions) {
            Permission permission = permissionRepository.findByResourceAndAction(resource, action);
            if (permission == null) {
                permission = new Permission(resource, action);
                permissionRepository.save(permission);
            }
        }
        return permissionRepository.findByResource(resource);
    }

    @Transactional
    Role createRoleIfNotFound(String name, Set<Permission> permissions) {

        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role newRole = new Role(name);
                    newRole.setPermissions(permissions);
                    return roleRepository.save(newRole);
                });
    }

    @Transactional
    void createAdminIfNotFound(Role role) {

        Optional<User> optionalUser = userRepository.findByEmail("admin@gmail.com");
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setFirstName("Test");
            user.setLastName("Admin");
            user.setPassword(passwordEncoder.encode("password"));
            user.setEmail("admin@gmail.com");
            user.setRoles(Collections.singleton(role));
            user.setEnabled(true);
            user.setAccountLocked(false);
            userRepository.save(user);
        }
    }
}
