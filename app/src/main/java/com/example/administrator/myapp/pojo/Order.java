package com.example.administrator.myapp.pojo;
import java.sql.Timestamp;
import java.util.List;




public class Order {
private Integer orderId;
private Integer userId;
private double orderPrice;
private OrderState goodsOrderState;//订单状态
private Timestamp goodsCreateTime;//订单创建时间
List<OrderDetail> orderDetails;//商品详情

public Order(Integer orderId, Integer userId, double orderPrice,
		OrderState goodsOrderState, Timestamp goodsCreateTime,
		List<OrderDetail> orderDetails) {
	super();
	this.orderId = orderId;
	this.userId = userId;
	this.orderPrice = orderPrice;
	this.goodsOrderState = goodsOrderState;
	this.goodsCreateTime = goodsCreateTime;
	this.orderDetails = orderDetails;
}
public double getOrderPrice() {
	return orderPrice;
}
public void setOrderPrice(double orderPrice) {
	this.orderPrice = orderPrice;
}
public Integer getOrderId() {
	return orderId;
}
public void setOrderId(Integer orderId) {
	this.orderId = orderId;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public OrderState getGoodsOrderState() {
	return goodsOrderState;
}
public void setGoodsOrderState(OrderState goodsOrderState) {
	this.goodsOrderState = goodsOrderState;
}
public Timestamp getGoodsCreateTime() {
	return goodsCreateTime;
}
public void setGoodsCreateTime(Timestamp goodsCreateTime) {
	this.goodsCreateTime = goodsCreateTime;
}
public List<OrderDetail> getOrderDetails() {
	return orderDetails;
}
public void setOrderDetails(List<OrderDetail> orderDetails) {
	this.orderDetails = orderDetails;
}
public Order(Integer orderId, Integer userId, OrderState goodsOrderState,
		Timestamp goodsCreateTime, List<OrderDetail> orderDetails) {
	super();
	this.orderId = orderId;
	this.userId = userId;
	this.goodsOrderState = goodsOrderState;
	this.goodsCreateTime = goodsCreateTime;
	this.orderDetails = orderDetails;
}
public Order(Integer userId, OrderState goodsOrderState,
		Timestamp goodsCreateTime, List<OrderDetail> orderDetails) {
	super();
	this.userId = userId;
	this.goodsOrderState = goodsOrderState;
	this.goodsCreateTime = goodsCreateTime;
	this.orderDetails = orderDetails;
}
public Order(){}

}
