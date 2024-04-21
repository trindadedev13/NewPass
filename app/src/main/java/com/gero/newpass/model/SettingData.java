package com.gero.newpass.model;

public class SettingData {
    int image;
    String name;
    Boolean isSwitchPresent;
    Boolean isImagePresent;

    public SettingData(int image, String name, Boolean isSwitchPresent, boolean isImagePresent) {
        this.image = image;
        this.name = name;
        this.isSwitchPresent = isSwitchPresent;
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
