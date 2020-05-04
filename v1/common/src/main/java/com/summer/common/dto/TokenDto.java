package com.summer.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * TokenDto
 *
 * Create By hmq
 * @date 2020/5/2 20:05
 */
@Data
public class TokenDto implements Serializable {
    private static final long serialVersionUID = -7991578981427632732L;

    private String access_token;
    private Date expiration;
    private Integer expires_in;
    private Map<String, Object> information;

    private TokenDto(Builder builder) {
        this.access_token = builder.access_token;
        this.expires_in = builder.expires_in;
        this.expiration = builder.expiration;
        this.information = builder.other;
    }

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder{
        private String access_token;
        private Date expiration;
        private Integer expires_in;
        private Map<String, Object> other;

        public Builder other(Map<String, Object> other){
            this.other = other;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.access_token = accessToken;
            return this;
        }

        public Builder expiration(Date expiration) {
            this.expiration = expiration;
            return this;
        }

        public Builder expiresIn(Integer expiresIn) {
            this.expires_in = expiresIn;
            return this;
        }

        public TokenDto build(){
            return new TokenDto(this);
        }

    }

}
