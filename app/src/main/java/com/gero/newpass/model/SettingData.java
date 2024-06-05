package com.gero.newpass.model;

public class SettingData {
    private int position, image;
    private String name;
    private Boolean isSwitchPresent;
    private Boolean isImagePresent;

    public int getSwitchID() {
        return switchID;
    }

    public void setSwitchID(int switchID) {
        this.switchID = switchID;
    }

    private int switchID;

    public SettingData(int position, int image, String name, boolean isImagePresent, Boolean isSwitchPresent, int switchID) {
        this.position = position;
        this.image = image;
        this.name = name;
        this.isImagePresent = isImagePresent;
        this.isSwitchPresent = isSwitchPresent;
        this.switchID = switchID;
    }

    public SettingData(int position, int image, String name, Boolean isImagePresent) {
        this.position = position;
        this.image = image;
        this.name = name;
        this.isImagePresent = isImagePresent;
        isSwitchPresent = false;
    }

    public SettingData(int position, int image, String name) {
        this.position = position;
        this.image = image;
        this.name = name;
        isSwitchPresent = false;
        isImagePresent = false;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSwitchPresence() {
        return isSwitchPresent;
    }

    public void setSwitchPresent(Boolean switchPresent) {
        isSwitchPresent = switchPresent;
    }

    public Boolean getImagePresence() {
        return isImagePresent;
    }

    public void setImagePresent(Boolean imagePresent) {
        isImagePresent = imagePresent;
    }
}
