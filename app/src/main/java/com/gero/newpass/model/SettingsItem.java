package com.gero.newpass.model;

public class SettingsItem {

    private String setting;
    private int icon;

    public SettingsItem(String setting, int icon) {
        this.setting = setting;
        this.icon = icon;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
