package com.example.administrator.myapp.pojo;

public class OrderState {
	private Integer orderStateId;
	private String orderStates;//订单状态
	public Integer getOrderStateId() {
		return orderStateId;
	}
	public void setOrderStateId(Integer orderStateId) {
		this.orderStateId = orderStateId;
	}
	public String getOrderStates() {
		return orderStates;
	}
	public void setOrderStates(String orderStates) {
		this.orderStates = orderStates;
	}
	public OrderState(Integer orderStateId, String orderStates) {
		super();
		this.orderStateId = orderStateId;
		this.orderStates = orderStates;
	}
	public OrderState(){}
}
