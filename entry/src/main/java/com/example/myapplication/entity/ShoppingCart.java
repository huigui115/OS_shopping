package com.example.myapplication.entity;

public class ShoppingCart {

	Long id;

	MyProduct myproduct;

	Tuser user;

	Integer count;
	
	public ShoppingCart() {
		super();
	}

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

	public Tuser getUser() {
		return user;
	}

	public void setUser(Tuser user) {
		this.user = user;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
