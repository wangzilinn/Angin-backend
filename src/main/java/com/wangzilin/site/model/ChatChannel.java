package com.***REMOVED***.site.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;

public class ChatChannel {
    @Id
    public String id;
    public String name;
    public Set<String> members;
    public Date createDate;

}
