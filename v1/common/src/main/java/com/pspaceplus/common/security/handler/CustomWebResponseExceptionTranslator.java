package com.pspaceplus.common.security.handler;

import com.pspaceplus.common.exception.CustomOauthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * CustomWebResponseExceptionTranslator
 * oauth exception 解析
 * Create By hmq
 * @date 2020/4/28 14:05
 */
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomOauthException(e.getMessage()));
    }
}
