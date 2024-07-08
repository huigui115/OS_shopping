package com.example.myapplication.entity;
public class FavProduct {

    Long id;
    MyProduct myproduct;
    Tuser user;
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
    public FavProduct(){super();}


}
