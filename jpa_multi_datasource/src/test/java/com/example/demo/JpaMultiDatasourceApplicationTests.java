package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.db1.Db1Repository;
import com.example.demo.db2.Db2Repository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaMultiDatasourceApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired 
	private Db2Repository db2Repository;
	
	@Autowired 
	private Db1Repository db1Repository;
	
	@Test
	public void 디비_확인(){
		System.out.println(db1Repository.findAll());
		
		System.out.println("중간");
		
		System.out.println(db2Repository.findAll());
	}
}
