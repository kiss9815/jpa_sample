package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;

@RestController
@RequestMapping("/")
public class MainController {

	
	
	@Autowired private BlogPostRepository blogPostRepository;
	 
    @RequestMapping
    public @ResponseBody String index() {
        return "Hello Woniper Spring Boot~";
    }
 
    @RequestMapping("/blog")
    public String getUserList() {
    	blogPostRepository.findAll();
        return "tt";
    }
}
