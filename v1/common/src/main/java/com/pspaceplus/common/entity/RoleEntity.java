package com.pspaceplus.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    private String name;
    private String powers;
    private String description;
}
