package com.xzm.springbootshiro.repository;

import com.xzm.springbootshiro.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    
}
