package com.example.myapplication.entity;

public class OrderProduct {

	private Long id;

	private MyProduct myproduct;

	private Double orderprice; //购买时的价格

	private Integer count;

	private Order order;//对应的订单

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MyProduct getMyproduct() {
		return myproduct;
	}

	public void setMyproduct(MyProduct myproduct) {
		this.myproduct = myproduct;
	}

	public Double getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(Double orderprice) {
		this.orderprice = orderprice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


	 
}