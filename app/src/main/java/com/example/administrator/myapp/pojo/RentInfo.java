package com.example.administrator.myapp.pojo;

import java.io.Serializable;

public class RentInfo implements Serializable {
	private int collectId;
	private String roomType;
	private int roomArea;
	private Double roomRent;
	private String photoImg;
	private User user;
	
	
	public RentInfo(String roomType, int roomArea, Double roomRent,
			String photoImg) {
		super();
		this.roomType = roomType;
		this.roomArea = roomArea;
		this.roomRent = roomRent;
		this.photoImg = photoImg;
	}
	public RentInfo(int collectId, String roomType, int roomArea,
			Double roomRent, String photoImg, User user) {
		super();
		this.collectId = collectId;
		this.roomType = roomType;
		this.roomArea = roomArea;
		this.roomRent = roomRent;
		this.photoImg = photoImg;
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RentInfo(){}
	public RentInfo(int collectId, String roomType, int roomArea,
			Double roomRent ,String photoImg) {
		super();
		this.collectId = collectId;
		this.roomType = roomType;
		this.roomArea = roomArea;
		this.roomRent = roomRent;
		this.photoImg=photoImg;
	}
	public int getCollectId() {
		return collectId;
	}
	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int getRoomArea() {
		return roomArea;
	}
	public void setRoomArea(int roomArea) {
		this.roomArea = roomArea;
	}
	public Double getRoomRent() {
		return roomRent;
	}
	public void setRoomRent(Double roomRent) {
		this.roomRent = roomRent;
	}
	
	public String getPhotoImg() {
		return photoImg;
	}
	public void setPhotoImg(String photoImg) {
		this.photoImg = photoImg;
	}

}
