package com.example.administrator.myapp.pojo;

import java.io.Serializable;

public class RentInfo implements Serializable {
	private int collectId;
	private String roomType;
	private int roomArea;
	private Double roomRent;
	private String photoImg;
	private User user;
	private String roomContent;
	private String roomAdress;
	private int rongliang;
	private String phoneNum;
	private String roomImg1;
	private int roomId;
	private String roomImg2;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomImg1() {
		return roomImg1;
	}

	public void setRoomImg1(String roomImg1) {
		this.roomImg1 = roomImg1;
	}

	public String getRoomImg2() {
		return roomImg2;
	}

	public void setRoomImg2(String roomImg2) {
		this.roomImg2 = roomImg2;
	}

	public RentInfo(int roomArea, Double roomRent, String roomContent,
					String roomAdress, int rongliang, String phoneNum) {
		super();
		this.roomArea = roomArea;
		this.roomRent = roomRent;
		this.roomContent = roomContent;
		this.roomAdress = roomAdress;
		this.rongliang = rongliang;
		this.phoneNum = phoneNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public RentInfo(int roomArea, Double roomRent, String roomContent,
			String roomAdress, int rongliang) {
		super();
		this.roomArea = roomArea;
		this.roomRent = roomRent;
		this.roomContent = roomContent;
		this.roomAdress = roomAdress;
		this.rongliang = rongliang;
	}
	public String getRoomContent() {
		return roomContent;
	}
	public void setRoomContent(String roomContent) {
		this.roomContent = roomContent;
	}
	public String getRoomAdress() {
		return roomAdress;
	}
	public void setRoomAdress(String roomAdress) {
		this.roomAdress = roomAdress;
	}
	public int getRongliang() {
		return rongliang;
	}
	public void setRongliang(int rongliang) {
		this.rongliang = rongliang;
	}
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
