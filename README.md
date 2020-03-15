## IM用户操作流程

1. 直接使用channel:
   1. 启动软件时,使用内嵌的用户名获得channel列表
      1. /UserChannelList, post:用户名, 密码 返回用户订阅channel列表
   2. 用户点击channel,进入聊天界面
      1. 获得对应channel的历史记录
         1. /ChannelHistory, post:用户名, 密码, channelName
      2. mqtt订阅该channel(以后在考虑订阅安全性)

   2.创建channel:

   1. 用户创建一个channel
      1. /NewChannel, post:用户名, 密码, channelName
   2. mqtt订阅新的channel

3. 加入channel:
   1. 用户搜索channel
      1. /ChannelList, post:用户名, 密码 返回所有channel
   2. 用户点击channel, 将该channel放入用户订阅的Channel列表中
      1. /AddChannel, post:用户名, 密码, channelName
   3. 即1.1

## IM服务器策略

1. /UserChannelList, post:用户名, 密码 返回用户订阅channel列表
   1. 验证用户
   2. getUserChannelList(userName): 用户订阅channel列表
2. /ChannelHistory
   1. 验证用户
   2. getChannelHistory(channelName): 消息记录
3. /NewChannel
   1. 验证用户
   2. setNewChannel(channelName)
      1. 订阅channelName
      2. 再chat中创建channelName
      3. 向user.channels添加channelName
      4. 向user.profile对应用户添加channelName

## IM数据库策略

1. DB: chat

   1. 不使用以用户为中心的储存策略, 使用以channel为中心的储存策略, 每个一对一或多对多的channel使用一个collection
   2. 每个collection是一个channel, collectionName是channelName

2. DB: user

   1. collection: profile 每个document是一个用户的所有数据

      ```json
      {
          "userName": "userName",
          "password": "password",
          "channels": ["channelName1", "channelName2]
      }
      ```

   2. collection: channels 每个document是一个channelName

   



