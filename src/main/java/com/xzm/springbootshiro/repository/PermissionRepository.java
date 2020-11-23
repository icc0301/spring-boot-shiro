package com.xzm.springbootshiro.repository;

import com.xzm.springbootshiro.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity,Integer> {
    
}
