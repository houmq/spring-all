package com.summer.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoEntity implements Serializable {

    private static final long serialVersionUID = -7504384717253838200L;

    private Integer id;
    private String loginname;
    private String password;
    private String headurl;
    private String username;
    private String telphone;
    private String mobile;
    private boolean enable;
    private String role;
    private Date addtime;
    private Date updatetime;
    private Date lastlogintime;
    private Integer areaid;
    private String deptcode;
}
