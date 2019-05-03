package com.example.demo.db2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.db2.BlogPost;

public interface Db2Repository extends JpaRepository<BlogPost, Long>  {

}
