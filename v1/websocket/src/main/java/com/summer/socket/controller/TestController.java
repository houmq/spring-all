package com.summer.socket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping(value = "/test")
    public String test(){
        return "websocket test" ;
    }


}
