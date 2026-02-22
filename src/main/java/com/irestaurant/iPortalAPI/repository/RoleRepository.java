package com.irestaurant.iPortalAPI.repository;

import com.irestaurant.iPortalAPI.model.DbRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<DbRole, Long> {
    Optional<DbRole> findByName(String name);
    boolean existsByName(String name);
}