package com.xzm.springbootshiro.repository;

import com.xzm.springbootshiro.entity.UserEntity;
import com.xzm.springbootshiro.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    

    
}
