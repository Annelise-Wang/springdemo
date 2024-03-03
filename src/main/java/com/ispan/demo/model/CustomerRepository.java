package com.ispan.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	@Query("from Customer where name = ?1")
	List<Customer> findCustomerByName(String name);
	
	List<Customer> findByName(String name);
	
	@Query("from Customer where name = :name")
	List<Customer> findCustomerByName2(@Param("name") String name);
	
	@Query("from Customer where name like %:name%")
	List<Customer> findByNameLike(@Param("name") String name);
	
	List<Customer> findByNameContaining(String name); 
	
	@Query("from Customer where name like %?1%")
	List<Customer> findByNameLike2(String name);
	
	@Query(value="select top (:num) * from customer order by id", nativeQuery = true)
	List<Customer> findCustomerNativeTop(@Param("num") Integer topNumber);
	
	@Transactional
	@Modifying  // HQL 本身只支援 DQL, 要寫 @Modifying 才有支援 DML
	@Query("update Customer set name = :newName where id = :id")
	Integer updateNameById(@Param("id") Integer id, @Param("newName") String newName);
	
	List<Customer> findByLevelOrderById(Integer level);

}


