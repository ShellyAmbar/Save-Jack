package com.shellyambar.thegame.Models;

public class UsersModel {

    private String userName;
    private String userPhoto;
    private String userId;
    private Integer totalScore;

    public UsersModel() {
    }

    public UsersModel(String userName, String userPhoto, String userId,Integer totalScore) {
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.userId = userId;
        this.totalScore = totalScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
