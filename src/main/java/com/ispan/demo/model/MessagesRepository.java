package com.ispan.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {
	
	Messages findFirstByOrderByAddedDesc();
	
	
	
	
	
	
	
	

}
