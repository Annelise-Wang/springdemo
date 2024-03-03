package com.ispan.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.demo.model.House;
import com.ispan.demo.model.HousePhoto;
import com.ispan.demo.model.HousePhotoRepository;
import com.ispan.demo.model.HouseRepository;

@Controller
public class HouseController {
	
	@Autowired
	private HouseRepository houseDao;
	
	@Autowired
	private HousePhotoRepository housePhotoDao;

	@GetMapping("/house/add")
	public String addHouse() {
		return "house/addHousePage";
	}
	
	@PostMapping("/house/add")
	public String postHouse(
			@RequestParam("houseName") String houseName, 
			@RequestParam("file") MultipartFile[] files ) throws IOException {
		
		House house = new House();
		house.setHousename(houseName);
		
		List<HousePhoto> housePhotoList = new ArrayList<>();
		
		for(MultipartFile file : files) {
			HousePhoto housePhoto = new HousePhoto();
			byte[] photoByte = file.getBytes();
			housePhoto.setPhotofile(photoByte);
			housePhoto.setHouse(house);
			
			housePhotoList.add(housePhoto);
		}
		
		house.setHousePhoto(housePhotoList);
		
		houseDao.save(house);
		
		
		return "house/addHousePage";
	}
	
	@GetMapping("/house/view")
	public String viewHousePhoto(Model model) {
		
		List<House> house = houseDao.findAll();
		
		model.addAttribute("house", house);
		
		return "house/viewHousePage";
	}
	
	@GetMapping("/house/photoDownload")
	public ResponseEntity<byte[]> downloadHousePhoto(@RequestParam("id") Integer id){
		Optional<HousePhoto> optional = housePhotoDao.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		
		HousePhoto housePhoto = optional.get();
		byte[] houseImageFile = housePhoto.getPhotofile();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		return new ResponseEntity<byte[]>(houseImageFile, headers,HttpStatus.OK);
	}
}
