package com.elcmal.config;

import com.elcmal.model.Role;
import com.elcmal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        initRoles();
    }

    private void initRoles() {
        // Initialize ROLE_USER if it doesn't exist
        if (!roleRepository.existsByName(Role.ERole.ROLE_USER)) {
            Role userRole = new Role();
            userRole.setName(Role.ERole.ROLE_USER);
            roleRepository.save(userRole);
        }

        // Initialize ROLE_ADMIN if it doesn't exist
        if (!roleRepository.existsByName(Role.ERole.ROLE_ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(Role.ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }
}
