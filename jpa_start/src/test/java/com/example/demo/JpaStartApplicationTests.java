package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaStartApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired 
	private BlogPostRepository blogPostRepository;
	
	@Test
	public void 디비_확인(){
		blogPostRepository.findAll();
	}
}
