package com.ispan.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.demo.model.Messages;
import com.ispan.demo.model.MessagesRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessagesRepository msgRepo;
	
	public void insert(Messages msg) {
		msgRepo.save(msg);
	}
	
	public Messages findById(Integer id) {
		Optional<Messages> optional = msgRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
	
	public void deleteById(Integer id) {
		msgRepo.deleteById(id);
	}
public Messages findLatest() {
	return msgRepo.findFirstByOrderByAddedDesc();
}
public Page<Messages> findByPage(Integer pageNumber){
	PageRequest pgb=PageRequest.of(pageNumber-1,3,Sort.Direction.DESC,"added");

	Page<Messages>page=msgRepo.findAll(pgb);
	return page;
}
}
