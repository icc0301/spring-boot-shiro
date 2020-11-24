package com.xzm.springbootshiro.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "user_role")
public class UserRoleEntity implements Serializable {

    @Id
    private Integer uid;

    @Id
    private Integer role_id;

}
