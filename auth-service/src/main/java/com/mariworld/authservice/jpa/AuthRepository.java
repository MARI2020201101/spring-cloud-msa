package com.mariworld.authservice.jpa;

import com.mariworld.authservice.vo.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    AuthEntity findByServiceId(String serviceId);
}
