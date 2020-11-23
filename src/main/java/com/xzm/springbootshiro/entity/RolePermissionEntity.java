package com.xzm.springbootshiro.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "role_permission")
public class RolePermissionEntity implements Serializable {

    @Id
    private Integer role_id;

    @Id
    private Integer permission_id;
}
