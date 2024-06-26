package com.cadastro1.demo.security;

import com.cadastro1.demo.model.ERole;
import com.cadastro1.demo.model.Role;
import com.cadastro1.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = Logger.getLogger(InitialDataLoader.class.getName());

    @Override
    public void run(String... args) throws Exception {
        logger.info("Checking roles in database...");
        if (roleRepository.count() == 0) {
            logger.info("No roles found. Inserting default roles...");
            List<Role> roles = Arrays.asList(
                    new Role(ERole.ROLE_USER),
                    new Role(ERole.ROLE_MODERATOR),
                    new Role(ERole.ROLE_ADMIN)
            );
            roleRepository.saveAll(roles);
            logger.info("Default roles inserted.");
        } else {
            logger.info("Roles already exist. Skipping insertion.");
        }
    }
}
