package com.***REMOVED***.site.model;

import org.springframework.data.annotation.Id;

public class ChannelModel {
    @Id
    public String id;
    public String name;

    public ChannelModel(String name) {
        this.name = name;
    }
}
