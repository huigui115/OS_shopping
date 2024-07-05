package com.example.myapplication.entity;


public class NavigationItem {
    private final int normalImageId;
    private final int selectedImageId;
    private final String text;

    public NavigationItem(int normalImageId, int selectedImageId, String text) {
        this.normalImageId = normalImageId;
        this.selectedImageId = selectedImageId;
        this.text = text;
    }
    public int getNormalImageId() {
        return normalImageId;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public String getText() {
        return text;
    }
}
