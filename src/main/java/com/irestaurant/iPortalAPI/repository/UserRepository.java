package com.irestaurant.iPortalAPI.repository;

import com.irestaurant.iPortalAPI.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DbUser, Long> {
    DbUser findByUsername(String username);
    DbUser findByEmail(String email);
    DbUser findByResetToken(String resetToken);
}
