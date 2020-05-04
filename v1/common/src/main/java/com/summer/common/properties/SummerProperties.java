package com.summer.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "summer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummerProperties {

    private String storeType;
    private String jwtKey;

}
