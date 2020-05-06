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

所有用户channelName以user-开头

1. /UserChannelList, post:用户名, 密码 返回用户订阅channel列表
   1. 验证用户
   2. getUserChannelList(userName): 用户订阅channel列表
2. /ChannelHistory
   1. 验证用户
   2. getChannelHistory(channelName): 消息记录
3. /NewChannel
   1. 验证用户
   2. setNewChannel(channelName)
      1. 判断该channel是否存在, 若存在, 则使用户加入该channel, 若不存在, 则创建channel并加入
         1. chat中创建channelName
         2. 向user.channels添加channelName
         3. 向user.profile对应用户添加channelName

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
          "channels": ["channelName1", "channelName2"]
      }
      ```

   2. collection: channels 每个document是一个channelName

   

# Tumo

数据库名:tumo

储存位置:

配置文件:/etc/mysql/conf.d/

数据:/data/mysql/

表结构:

1. tb_article
    | Field        | Type         | Null | Key | Default | Extra          |
    |--------------|--------------|------|-----|---------|----------------|
    | id           | bigint       | NO   | PRI | NULL    | auto_increment |
    | title        | varchar(400) | YES  |     | NULL    |                |
    | cover        | varchar(400) | YES  |     | NULL    |                |
    | author       | varchar(100) | NO   |     | NULL    |                |
    | content      | mediumtext   | YES  |     | NULL    |                |
    | content_md   | mediumtext   | YES  |     | NULL    |                |
    | category     | varchar(20)  | YES  |     | NULL    |                |
    | state        | varchar(100) | NO   |     | NULL    |                |
    | publish_time | datetime     | YES  |     | NULL    |                |
    | edit_time    | datetime     | NO   |     | NULL    |                |
    | create_time  | datetime     | NO   |     | NULL    |                |
    | type         | int          | YES  |     | 0       |                |

3. tb_article_category

   | Field       | Type   | Null | Key | Default | Extra          |
   |-------------|--------|------|-----|---------|----------------|
   | id          | bigint | NO   | PRI | NULL    | auto_increment |
   | article_id  | bigint | NO   |     | NULL    |                |
   | category_id | bigint | NO   |     | NULL    |                |

3. tb_article_tag

    | Field      | Type   | Null | Key | Default | Extra          |
    |------------|--------|------|-----|---------|----------------|
    | id         | bigint | NO   | PRI | NULL    | auto_increment |
    | article_id | bigint | NO   |     | NULL    |                |
    | tag_id     | bigint | NO   |     | NULL    |                |

4. tb_category

    | Field | Type         | Null | Key | Default | Extra          |
    |-------|--------------|------|-----|---------|----------------|
    | id    | bigint       | NO   | PRI | NULL    | auto_increment |
    | name  | varchar(100) | YES  |     | NULL    |                |

5. tb_comment

    | Field         | Type         | Null | Key | Default | Extra          |
    |---------------|--------------|------|-----|---------|----------------|
    | id            | bigint       | NO   | PRI | NULL    | auto_increment |
    | p_id          | bigint       | YES  |     | 0       |                |
    | c_id          | bigint       | YES  |     | 0       |                |
    | article_title | varchar(200) | YES  |     | NULL    |                |
    | article_id    | bigint       | YES  |     | NULL    |                |
    | name          | varchar(20)  | YES  |     | NULL    |                |
    | c_name        | varchar(20)  | YES  |     | NULL    |                |
    | time          | datetime     | NO   |     | NULL    |                |
    | content       | text         | YES  |     | NULL    |                |
    | email         | varchar(100) | YES  |     | NULL    |                |
    | url           | varchar(200) | YES  |     | NULL    |                |
    | sort          | bigint       | YES  |     | 0       |                |
    | ip            | varchar(20)  | YES  |     | NULL    |                |
    | device        | varchar(100) | YES  |     | NULL    |                |
    | address       | varchar(100) | YES  |     | NULL    |                |

6. tb_link

    | Field | Type         | Null | Key | Default | Extra          |
    |-------|--------------|------|-----|---------|----------------|
    | id    | bigint       | NO   | PRI | NULL    | auto_increment |
    | name  | varchar(100) | YES  |     | NULL    |                |
    | url   | varchar(200) | YES  |     | NULL    |                |

7. tb_log

    | Field       | Type         | Null | Key | Default | Extra          |
    |-------------|--------------|------|-----|---------|----------------|
    | id          | bigint       | NO   | PRI | NULL    | auto_increment |
    | username    | varchar(20)  | YES  |     | NULL    |                |
    | operation   | varchar(20)  | YES  |     | NULL    |                |
    | time        | bigint       | YES  |     | NULL    |                |
    | method      | varchar(100) | YES  |     | NULL    |                |
    | params      | varchar(255) | YES  |     | NULL    |                |
    | ip          | varchar(20)  | YES  |     | NULL    |                |
    | create_time | datetime     | YES  |     | NULL    |                |
    | location    | varchar(20)  | YES  |     | NULL    |                |

8. tb_tag

    | Field | Type         | Null | Key | Default | Extra          |
    |-------|--------------|------|-----|---------|----------------|
    | id    | bigint       | NO   | PRI | NULL    | auto_increment |
    | name  | varchar(100) | YES  |     | NULL    |                |

9. tb_user

    | Field     | Type         | Null | Key | Default | Extra          |
    |-----------|--------------|------|-----|---------|----------------|
    | id        | bigint       | NO   | PRI | NULL    | auto_increment |
    | username  | varchar(100) | NO   |     | NULL    |                |
    | password  | varchar(100) | NO   |     | NULL    |                |
    | salt      | varchar(200) | NO   |     | NULL    |                |
    | avatar    | varchar(200) | YES  |     | NULL    |                |
    | introduce | varchar(100) | YES  |     | NULL    |                |
    | remark    | varchar(100) | YES  |     | NULL    |                |

