package com.linktreeclone.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linktreeclone.api.model.ERole;
import com.linktreeclone.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(@Param("name") ERole name);
}
