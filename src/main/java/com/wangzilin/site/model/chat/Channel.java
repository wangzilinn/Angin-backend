package com.wangzilin.site.model.chat;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Channel {
    @Id
    private Long id;

    private String name;
    private Set<String> usernames;
    private Date createDate;

    /**
     * created by user
     *
     * @param channelName .
     * @param creatorName creator user name.
     */
    public Channel(String channelName, String creatorName) {
        this.name = channelName;
        usernames = new HashSet<>();
        usernames.add(creatorName);
        createDate = new Date();
    }
}
