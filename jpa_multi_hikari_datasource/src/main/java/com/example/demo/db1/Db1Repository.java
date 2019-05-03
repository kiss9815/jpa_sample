package com.example.demo.db1;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.db1.BlogPosted;

public interface Db1Repository extends JpaRepository<BlogPosted, Long>  {

}
