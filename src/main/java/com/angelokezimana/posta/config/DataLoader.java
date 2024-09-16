package com.angelokezimana.posta.config;


import com.angelokezimana.posta.entity.security.Permission;
import com.angelokezimana.posta.entity.security.Role;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.entity.security.UserStatus;
import com.angelokezimana.posta.repository.security.PermissionRepository;
import com.angelokezimana.posta.repository.security.RoleRepository;
import com.angelokezimana.posta.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        List<Permission> rolePermission = createPermissionIfNotFound("ROLE");
        List<Permission> userPermission = createPermissionIfNotFound("USER");
        List<Permission> blogPermission = createPermissionIfNotFound("BLOG");

        Set<Permission> adminPrivileges = new HashSet<>();

        adminPrivileges.addAll(rolePermission);
        adminPrivileges.addAll(userPermission);
        adminPrivileges.addAll(blogPermission);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        createAdminIfNotFound(adminRole);

        alreadySetup = true;
    }

    @Transactional
    List<Permission> createPermissionIfNotFound(String name) {

        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = new Permission(name);
            permissionRepository.save(permission);

            List<String> subPermissions = Arrays.asList("CREATE", "READ", "UPDATE", "DELETE");
            System.out.println("getPermission=" + permission.getId());

            for (String subPermission : subPermissions) {
                Permission subPerm = new Permission(subPermission);
                subPerm.setParent(permission);
                permissionRepository.save(subPerm);
            }
        }
        return permissionRepository.getParent(permission);
    }

    @Transactional
    void createRoleIfNotFound(String name, Set<Permission> permissions) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
    }

    @Transactional
    void createAdminIfNotFound(Role role) {

        Optional<User> optionalUser = userRepository.findByEmail("test@test.com");
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setRoles(Collections.singleton(role));
            user.setStatus(UserStatus.ACTIVE.getValue());
            userRepository.save(user);
        }
    }
}
