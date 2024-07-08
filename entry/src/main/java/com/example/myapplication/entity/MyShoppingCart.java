package com.example.myapplication.entity;

public class MyShoppingCart {
	Long id;
	MyProduct myproduct;
	Tuser user;
	Integer count;
	Integer select = 0;
	
	public MyShoppingCart() {
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

	//getSelect与setSelect方法
    public int getSelect() {

		return select;
    }

	public void setSelect(int i) {
		this.select=i;
	}
}
