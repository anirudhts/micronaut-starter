package com.project.controller;


import com.project.service.HelloWorldService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;

@Controller("/home")
public class HelloWorldController {


    private final HelloWorldService helloWorldService; //NOPMD

    @Inject
    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @Get("/hello")
    public String helloWorld() {
        return helloWorldService.hello();
    }

}
