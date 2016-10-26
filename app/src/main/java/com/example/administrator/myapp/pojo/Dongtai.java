package com.example.administrator.myapp.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class Dongtai implements Serializable {
	public Integer dongtaiId;
	public String title;
	public String dongtaiImage;
	public String content;
	public Timestamp publishtime;
	public User user;
	public String adress;
	public boolean type;
	public List<Remark>remarklist;
	public List<Dianzan>dianzanlist;
	    
	    
	    
		public Dongtai(){}
		
		public Dongtai(Integer dongtaiId, String title, String content,
				Timestamp publishtime,User user) {
			super();
			this.dongtaiId = dongtaiId;
			this.title = title;
			this.content = content;
			this.publishtime = publishtime;
			this.user = user;
		}




		public Integer getDongtaiId() {
			return dongtaiId;
		}




		public void setDongtaiId(Integer dongtaiId) {
			this.dongtaiId = dongtaiId;
		}




		public String getTitle() {
			return title;
		}




		public void setTitle(String title) {
			this.title = title;
		}




		public String getContent() {
			return content;
		}




		public void setContent(String content) {
			this.content = content;
		}




		public Timestamp getPublishtime() {
			return publishtime;
		}




		public void setPublishtime(Timestamp publishtime) {
			this.publishtime = publishtime;
		}




		public User getUser() {
			return user;
		}




		public void setUser(User userinfo) {
			this.user = user;
		}

	    
	    

	   
	}



