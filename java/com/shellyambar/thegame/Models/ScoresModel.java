package com.shellyambar.thegame.Models;

public class ScoresModel {

    private String userName;
    private String userPhoto;
    private int userScore;

    public ScoresModel(String userName, String userPhoto, int userScore) {
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.userScore = userScore;
    }

    public ScoresModel() {
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

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
}
