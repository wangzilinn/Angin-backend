package com.***REMOVED***.site.model.chat;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 5:32 PM 5/11/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@NoArgsConstructor
public class UserChannel {

    @Id
    private String id;

    private String username;

    private Set<String> channels;

    //只有用户创建了channel,才新建一个userChannel对象
    public UserChannel(String username, String channelName) {
        this.username = username;
        channels = new HashSet<>();
        channels.add(channelName);
    }
}
