package com.example.demo;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="boilerplate.blog_post")
public class BlogPost {

	@Id
	private Long id;
	
	@Column(name = "title")
    private String title;
	
	
}
