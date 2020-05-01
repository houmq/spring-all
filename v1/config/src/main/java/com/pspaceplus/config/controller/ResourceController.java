package com.pspaceplus.config.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/r")
public class ResourceController {


    @RequestMapping(value = "/a")
    @PreAuthorize("hasAuthority('p1')")
    public String test(){
        return "ok";
    }

    @RequestMapping(value = "/b")
    @PreAuthorize("hasAuthority('p2')")
    public String test2(){
        return "ok2222";
    }

}
