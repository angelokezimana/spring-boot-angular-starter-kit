package com.angelokezimana.posta.config;


import com.angelokezimana.posta.entity.security.Permission;
import com.angelokezimana.posta.entity.security.Role;
import com.angelokezimana.posta.entity.security.User;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        List<Permission> rolePermission = createPermissionIfNotFound("ROLE");
        List<Permission> userPermission = createPermissionIfNotFound("USER");
        List<Permission> blogPermission = createPermissionIfNotFound("BLOG");

        List<Permission> adminPrivileges = new ArrayList<>();

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
            System.out.println("getPermission="+permission.getId());

            for (String subPermission : subPermissions) {
                Permission subPerm = new Permission(subPermission);
                subPerm.setParent(permission);
                permissionRepository.save(subPerm);
            }
        }
        return permissionRepository.getParent(permission);
    }

    @Transactional
    void createRoleIfNotFound(String name, Collection<Permission> permissions) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
    }

    @Transactional
    void createAdminIfNotFound(Role role) {

        User user = userRepository.findByEmail("test@test.com");
        if (user == null) {
            user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setRoles(Collections.singleton(role));
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}