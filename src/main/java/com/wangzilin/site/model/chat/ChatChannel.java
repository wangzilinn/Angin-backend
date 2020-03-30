package com.***REMOVED***.site.model.chat;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ChatChannel {
    @Id
    public String id;
    public String name;
    public Set<String> members;
    public Date createDate;

    /**
     * for mongoDB
     */
    public ChatChannel() {
    }

    /**
     * created by user
     *
     * @param channelName .
     * @param creatorId   creator userId.
     */
    public ChatChannel(String channelName, String creatorId) {
        this.name = channelName;
        members = new HashSet<>();
        members.add(creatorId);
        createDate = new Date();
    }
}
