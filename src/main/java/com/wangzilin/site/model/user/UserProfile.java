package com.***REMOVED***.site.model.user;

import org.springframework.data.annotation.Id;

import java.util.List;

public class UserProfile {
    @Id
    private String id;
    private String userId;
    private String password;
    private List<String> channels;
    private String role;

    //给mongoDB用
    public UserProfile() {
    }

    public UserProfile(String id, String userId, String password, List<String> channels, String role) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.channels = channels;
        this.role = role;
    }

    public UserProfile(String userId, String password) {
        this(null, userId, password, null, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
