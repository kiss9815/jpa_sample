package com.example.demo.db2_1;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.db2.BlogPost;

public interface Db2_1Repository extends JpaRepository<BlogPost, Long>  {

}
