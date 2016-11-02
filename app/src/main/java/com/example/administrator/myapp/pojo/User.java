package com.example.administrator.myapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class User implements Parcelable {
	private Integer userId;
	private String userName;
	private Boolean sex;
	private String phoneNum;
	private String photoImg;
     private String userPsd;
	private String interestLabel;
	private Boolean isLandlord;
	private String realName;
	private String cardNum;
	private String cardZheng;
	private String cardFan;
	private String token;
    private Timestamp birthday;

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCardZheng() {
		return cardZheng;
	}

	public void setCardZheng(String cardZheng) {
		this.cardZheng = cardZheng;
	}

	public String getCardFan() {
		return cardFan;
	}

	public void setCardFan(String cardFan) {
		this.cardFan = cardFan;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	protected User(Parcel in) {
		userName = in.readString();
		phoneNum = in.readString();
		photoImg = in.readString();
		userPsd=in.readString();
		interestLabel=in.readString();
		realName=in.readString();
		cardNum=in.readString();
		cardZheng=in.readString();
		cardFan=in.readString();

	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public Boolean getLandlord() {
		return isLandlord;
	}

	public void setLandlord(Boolean landlord) {
		isLandlord = landlord;
	}

	public String getInterestLabel() {
		return interestLabel;
	}

	public void setInterestLabel(String interestLabel) {
		this.interestLabel = interestLabel;
	}

	public String getUserPsd() {
		return userPsd;
	}
	public void setUserPsd(String userPsd) {
		this.userPsd = userPsd;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPhotoImg() {
		return photoImg;
	}
	public void setPhotoImg(String photoImg) {
		this.photoImg = photoImg;
	}
	public User(Integer userId, String userName, Boolean sex, String phoneNum,
			String photoImg) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.sex = sex;
		this.phoneNum = phoneNum;
		this.photoImg = photoImg;
	}
	public User(Integer userId,String userName){}
	public User(){}

	public User(String photoImg, String userPsd, String interestLabel, Boolean isLandlord, String userName) {
		this.photoImg = photoImg;
		this.userPsd = userPsd;
		this.interestLabel = interestLabel;
		this.isLandlord = isLandlord;
		this.userName = userName;
	}

	public User(String userName, String photoImg, String userPsd, String interestLabel) {
		this.userName = userName;
		this.photoImg = photoImg;
		this.userPsd = userPsd;
		this.interestLabel = interestLabel;
	}

	public User(String userName, Integer userId, String userPsd) {
		this.userName = userName;
		this.userId = userId;
		this.userPsd = userPsd;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userName);
		dest.writeString(phoneNum);
		dest.writeString(photoImg);
		dest.writeString(userPsd);
		dest.writeString(interestLabel);
		dest.writeString(realName);
		dest.writeString(cardNum);
		dest.writeString(cardZheng);
        dest.writeString(cardFan);
	}
}
