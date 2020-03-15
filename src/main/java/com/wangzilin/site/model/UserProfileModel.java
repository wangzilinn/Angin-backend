package com.***REMOVED***.site.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class UserProfileModel {
    @Id
    public String id;
    public String userName;
    public String password;
    public List<String> channels;
}
