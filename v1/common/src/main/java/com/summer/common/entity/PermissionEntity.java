package com.summer.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity implements Serializable {

    private static final long serialVersionUID = 2572326469057470491L;
    private Integer id;
    private Integer role_id;
    private String permissions;
    private Date addtime;
    private Date updatetime;

}
