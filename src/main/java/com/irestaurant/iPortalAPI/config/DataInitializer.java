package com.irestaurant.iPortalAPI.config;

import com.irestaurant.iPortalAPI.enumerators.Roles;
import com.irestaurant.iPortalAPI.model.DbRole;
import com.irestaurant.iPortalAPI.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        createRoleIfNotFound(Roles.User.name());
        createRoleIfNotFound(Roles.Admin.name());
    }

    private void createRoleIfNotFound(String name) {
        if (roleRepository.findByName(name).isEmpty()) {
            DbRole role = new DbRole(name);
            roleRepository.save(role);
        }
    }
}