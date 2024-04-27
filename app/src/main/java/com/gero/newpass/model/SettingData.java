package com.gero.newpass.model;

public class SettingData {
    private int image;
    private String name;
    private Boolean isSwitchPresent;
    private Boolean isImagePresent;

    public SettingData(int image, String name, boolean isImagePresent, Boolean isSwitchPresent) {
        this.image = image;
        this.name = name;
        this.isImagePresent = isImagePresent;
        this.isSwitchPresent = isSwitchPresent;
    }

    public SettingData(int image, String name) {
        this.image = image;
        this.name = name;
        isSwitchPresent = false;
        isImagePresent = false;
    }

    public SettingData(int image, String name, boolean isImagePresent) {
        this.image = image;
        this.name = name;
        isSwitchPresent = false;
        this.isImagePresent = isImagePresent;
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
