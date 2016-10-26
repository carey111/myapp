package com.example.administrator.myapp.pojo;



public class OrderDetail {
	private Integer orderDetailId;//详情表主键
	private Integer orderId;//订单表id
	private RentInfoBean.RentInfo rentInfo;//房间信息
	private double orderPrice;
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public RentInfoBean.RentInfo getRentInfo() {
		return rentInfo;
	}
	public void setRentInfo(RentInfoBean.RentInfo rentInfo) {
		this.rentInfo = rentInfo;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public OrderDetail(Integer orderDetailId, Integer orderId,
					   RentInfoBean.RentInfo rentInfo, double orderPrice) {
		super();
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.rentInfo = rentInfo;
		this.orderPrice = orderPrice;
	}
	public OrderDetail(Integer orderId, RentInfoBean.RentInfo rentInfo, double orderPrice) {
		super();
		this.orderId = orderId;
		this.rentInfo = rentInfo;
		this.orderPrice = orderPrice;
	}
	public OrderDetail(){}
}
