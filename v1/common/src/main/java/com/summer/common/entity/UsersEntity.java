package com.summer.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    private String name;
    private String password;
    private String power;
    private String group_name;
    private String description;

}
