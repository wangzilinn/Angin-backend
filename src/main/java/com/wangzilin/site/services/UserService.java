package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.UserDAO;
import com.***REMOVED***.site.model.chat.ChatChannel;
import com.***REMOVED***.site.model.user.UserForAuth;
import com.***REMOVED***.site.model.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDAO userDAO;

    //传入从网页来的json
    public void addUser(String json) {

    }

    @Deprecated
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

    @Override
    public UserForAuth loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserProfile userProfile = userDAO.findUser(userId);
        //将从数据库获取的user转为专为用户验证的user
        return new UserForAuth(userProfile);
    }
}
