package com.summer.config.controller;


import com.summer.common.annotation.CurrentUser;
import com.summer.common.entity.UserInfoEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/r")
public class ResourceController {


    @RequestMapping(value = "/a")
    @PreAuthorize("hasAuthority('/r/a')")
    public String test(@CurrentUser UserInfoEntity userInfoEntity){
        String username = userInfoEntity.getUsername();
        System.out.println(username);
        return "ok" + username;
    }

    @RequestMapping(value = "/b")
    @PreAuthorize("hasAuthority('/r/b')")
    public String test2(){
        return "ok2222";
    }

}
