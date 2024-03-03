package com.ispan.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);
}