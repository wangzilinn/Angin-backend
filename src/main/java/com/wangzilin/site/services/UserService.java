package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.UserDAO;
import com.***REMOVED***.site.model.UserProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    //传入从网页来的json
    public void addUser(String json) {

    }

    public boolean authenticateUser(String userName, String password) {
        UserProfileModel userProfile = userDAO.findUser(userName);
        return userProfile.password.equals(password);
    }

    public List<String> getUserChannels(String userName) {
        UserProfileModel userProfile = userDAO.findUser(userName);
        return userProfile.channels;
    }


}
