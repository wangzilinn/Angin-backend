package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.UserDAO;
import com.***REMOVED***.site.model.ChatChannel;
import com.***REMOVED***.site.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    //传入从网页来的json
    public void addUser(String json) {

    }

    public boolean authenticateUser(String userId, String password) {
        UserProfile userProfile = userDAO.findUser(userId);
        return userProfile.getPassword().equals(password);
    }

    public List<ChatChannel> getUserChannels(String userId) {
        UserProfile userProfile = userDAO.findUser(userId);
        ArrayList<ChatChannel> channels = new ArrayList<>();
        for (String userChannel : userProfile.getChannels()) {
            channels.add(userDAO.findChannel(userChannel));
        }
        return channels;
    }
}
