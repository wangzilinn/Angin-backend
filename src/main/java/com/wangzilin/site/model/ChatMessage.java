package com.***REMOVED***.site.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ChatMessage {
    @Id
    public String id;
    public String userId;
    public String peerId;
    public String type;
    public String content;
    public Date dateTime;
}
