package com.ispan.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.demo.model.Customer;
import com.ispan.demo.model.CustomerRepository;

@RestController
public class CustomerApi {
	
	@Autowired
	private CustomerRepository cusRepo;
	
	@PostMapping("/customer/add")
	public Customer addCustomer() {
		Customer cus1 = new Customer();
		cus1.setName("館長");
		cus1.setLevel(3);
		
		Customer responseCustomer = cusRepo.save(cus1);
		
		return responseCustomer;
	}

	@PostMapping("/customer/add2")
	public Customer addCustomer2(@RequestBody Customer cus) {
Customer responseCustomer=cusRepo.save(cus);
return responseCustomer;
}
	@PostMapping("/customer/addsome")
	public List<Customer> addSomeCustomer(@RequestBody List<Customer> customerList){
		return cusRepo.saveAll(customerList);
	}
	
	@GetMapping("/customer/{id}")
	public Customer findCustomerById(@PathVariable("id") Integer id) {
		Optional<Customer> optional=cusRepo.findById(id);
		
		if(optional.isPresent()) {
			Customer result=optional.get();
			return result;
		}
		Customer errorMsg=new Customer();
		errorMsg.setId(0);
		errorMsg.setName("沒有這筆資料");
		return errorMsg;
	}
	
	@GetMapping("/customer/all")
	//兩個都一樣
//	public Iterable<Customer>findAllCustomer(){
//		Iterable<Customer> result= cusRepo.findAll();
	public List<Customer>findAllCustomer(){
			List<Customer> result= cusRepo.findAll();
return result;
	}
	
	@DeleteMapping("/customer/delete")
	public String deleteById(@RequestParam("id") Integer id) {
		Optional<Customer> optional=cusRepo.findById(id);
	
	if(optional.isEmpty()) {
		return "沒有這筆資料!!";
	}
	
	cusRepo.deleteById(id);
	return "刪除成功!!";
	}
	
	//透過ID修改某筆資料的等級
	@Transactional //要蓋過SimpleJpaRepository預設的 read-only交易
	@PutMapping("/customer/update")
	public String UpdateLevelById(@RequestParam("id") Integer id,@RequestParam("level") Integer level) {
		Optional<Customer> optional=cusRepo.findById(id);
		
		if(optional.isPresent()) {
			
			Customer customer=optional.get();
			customer.setLevel(level);
			return "修改OK";
			
		}
		
		return ("沒有這筆資料");
		
	}
	@GetMapping("/customer/page/{pageNumber}")
	public Page<Customer> findByPage(@PathVariable Integer pageNumber){
		Pageable pgb = PageRequest.of(pageNumber-1, 2, Sort.Direction.ASC, "id");
//		PageRequest pgb = PageRequest.of(pageNumber-1, 3, Sort.Direction.ASC, "id");
		
		Page<Customer> Page = cusRepo.findAll(pgb);
		
//		List<Customer> listData = page.getContent();
		return Page;
		
		//return listData;
	}
	
	@GetMapping("/customer/name")
	public List<Customer> findCustomerByName(@RequestParam("n") String name){
//		return cusRepo.findCustomerByName2(name);
//		return cusRepo.findByNameLike2(name);
//		return cusRepo.findByName(name);
//		return cusRepo.findByNameContaining(name);
		
		String withWirdCard = "%" + name + "%";
		
		return cusRepo.findByNameLike(withWirdCard);
	}
	
	@GetMapping("/customer/top")
	public List<Customer> findCustomerTopNative(@RequestParam Integer topNum){
		return cusRepo.findCustomerNativeTop(topNum);
	}
	
	@PutMapping("/customer/updateQuery")
	public Integer updateDataWithQuery(@RequestParam("id") Integer id, @RequestParam("newName") String newName) {
		return cusRepo.updateNameById(id,newName);
	}
	
	@GetMapping("/customer/level")
	public List<Customer> testJpqlSnippet(@RequestParam("level") Integer level){
		return cusRepo.findByLevelOrderById(level);
	}

}



