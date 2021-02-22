package com.revature.kingkiller.util;

/**
 * Configuration object to hold the initial login credentials after scraping
 */
public class ConfigData {
    private String url;
    private String username;
    private String password;

    /**
     * Default ConfigData constructor
     */
    public ConfigData() {
    }

    /**
     * @return String url for database initialization
     */
    public String getUrl() {
        return url;
    }

    /**
     * set String url for database initialization
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return String username for database initialization
     */
    public String getUsername() {
        return username;
    }

    /**
     * set String username for database initialization
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String password for database initialization
     */
    public String getPassword() {
        return password;
    }

    /**
     * set String password for database initialization
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
