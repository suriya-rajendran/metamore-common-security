package com.rjs.code.studio.metamore.common.security.repo;

import com.rjs.code.studio.metamore.common.security.datamodel.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserDetails, Long> {
    UserDetails findByUsername(String username);
}
