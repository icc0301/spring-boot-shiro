package com.xzm.springbootshiro.entity;


import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private Set<UserRoleEntity> userRoleEntity;

}
