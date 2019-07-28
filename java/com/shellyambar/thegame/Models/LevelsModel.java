package com.shellyambar.thegame.Models;

public class LevelsModel {

    private int levelNum;
    private String isOpen;


    public LevelsModel() {
    }

    public LevelsModel(int levelNum, String isOpen) {
        this.levelNum = levelNum;
        this.isOpen = isOpen;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }
}
