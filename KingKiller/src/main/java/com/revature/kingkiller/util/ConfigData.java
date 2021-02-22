package com.revature.kingkiller.util;

public class ConfigData {
    private String url;
    private String username;
    private String password;

    public ConfigData(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ConfigData(String cfgPath) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ConfigData() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
