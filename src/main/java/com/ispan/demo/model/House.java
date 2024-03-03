package com.ispan.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="house")
public class House {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="housename")
	private String housename;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="house")
	private List<HousePhoto> housePhoto=new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHousename() {
		return housename;
	}

	public void setHousename(String housename) {
		this.housename = housename;
	}

	public List<HousePhoto> getHousePhoto() {
		return housePhoto;
	}

	public void setHousePhoto(List<HousePhoto> housePhotoList){
		this.housePhoto = housePhoto;
	}

	
	
	
}
