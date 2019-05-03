package com.example.demo.db1;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name="blog_posted")
public class BlogPosted {

	@Id
	private Long id;
	
	@Column(name = "title")
    private String title;
	
	
}
