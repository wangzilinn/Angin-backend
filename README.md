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

先照抄了一边这个:https://github.com/TyCoding/tumo-vue

## 数据库

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

## 请求

### 管理界面登陆时:

1. login

   POST:http://127.0.0.1:8080/login?username=tycoding&password=123456

    ```json
   {"code":200,"msg":"success","data":{"token":"d2956a3b-785c-403e-809c-aac364fb7669"}}
    ```

1. info: 

   GET:http://127.0.0.1:8080/api/user/info

   Authorization: d2956a3b-785c-403e-809c-aac364fb7669
   
   ```json
{"code":200,"msg":"success","data":{"id":1,"username":"tycoding","password":"5f9059b3feff398c928c7c1239e64975","salt":"afbe4bd05b55b755d2a3e7df3bc25586","avatar":"http://img.api.tycoding.cn/1568958650973.jpeg","introduce":"兴趣使然的Coder","remark":"银河街角，时光路口"}}
   ```

**这里修改**

使用/login请求时,直接返回全部用户数据,删除info请求

1. list 

   POST:http://127.0.0.1:8080/api/article/list?page=1&limit=10

   Authorization: d2956a3b-785c-403e-809c-aac364fb7669

   ```json
   {"code":200,"msg":"success","data":{"total":1,"rows":[{"id":11,"title":"12345","cover":null,"author":"tycoding","content":"<p>133423乳房犯得上示范点</p>\n","contentMd":"133423乳房犯得上示范点","category":"测试","state":"1","publishTime":null,"editTime":"2020-05-06 21:24:30","createTime":"2020-05-06 21:24:30","tags":[{"id":4,"name":"测试","count":null}]}]}}
   ```

1. list

   POST:http://127.0.0.1:8080/api/comment/list?page=1&limit=10

   ```json
   {"code":200,"msg":"success","data":{"total":0,"rows":[]}}
   ```

### 新建文章

1. findAll

   GET:http://127.0.0.1:8080/api/category/findAll

   ```json
   {"code":200,"msg":"success","data":[{"id":1,"name":"测试"},{"id":2,"name":"随笔"},{"id":3,"name":"心情"},{"id":4,"name":"springboot"}]}
   ```

2. findAll

   GET:http://127.0.0.1:8080/api/tag/findAll

   ```json
   {"code":200,"msg":"success","data":[{"id":1,"name":"随笔","count":null},{"id":4,"name":"测试","count":null},{"id":5,"name":"博客日志","count":null}]}
   ```

3. article

   POST:http://127.0.0.1:8080/api/article

   ```json
   {
       category: 2
   	content: "<p>范德萨范德萨范德萨范<strong>德萨范德萨</strong>发大水<br>↵广泛大使馆<i"
   	contentMd: "范德萨范德萨范德萨范**德萨范德萨**发大水↵广泛大使馆![S80221-223450.jpg](d"
   	state: 1
   	tags: [{id: 1}, {id: 4}]
   	title: "哈哈哈哈哈"
   }
   ```

   ```json
   {"code":200,"msg":"success","data":null}
   ```

   提交后直接跳转到`文章列表`页

### 文章列表

4. list

   POST:http://127.0.0.1:8080/api/article/list?page=1&limit=20

   ```json
   {"code":200,"msg":"success","data":{"total":2,"rows":[{"id":12,"title":"哈哈哈哈哈","cover":null,"author":"tycoding","content":"<p>范德萨范德萨范德萨范<strong>德萨范德萨</strong>发大水<br>\n广泛大使馆","category":"随笔","state":"1","publishTime":null,"editTime":"2020-05-10 11:50:03","createTime":"2020-05-10 11:50:03","tags":[{"id":1,"name":"随笔","count":null},{"id":4,"name":"测试","count":null}]},{"id":11,"title":"12345","cover":null,"author":"tycoding","content":"<p>133423乳房犯得上示范点</p>\n","contentMd":"133423乳房犯得上示范点","category":"测试","state":"1","publishTime":null,"editTime":"2020-05-06 21:24:30","createTime":"2020-05-06 21:24:30","tags":[{"id":4,"name":"测试","count":null}]}]}}
   ```

2. findAll

   获得所有分类,以便查询文章

   GET:http://127.0.0.1:8080/api/category/findAll

   ```json
   {"code":200,"msg":"success","data":[{"id":1,"name":"测试"},{"id":2,"name":"随笔"},{"id":3,"name":"心情"},{"id":4,"name":"springboot"}]}
   ```

3. 编辑文章:

   GET http://127.0.0.1:8080/api/article/13 (文章ID)

   ```json
   {"code":200,"msg":"success","data":{"id":13,"title":"1233","cover":null,"author":"tycoding","content":"<p>321332131</p>\n","contentMd":"321332131","category":"测试","state":"1","publishTime":null,"editTime":"2020-05-10 11:56:47","createTime":"2020-05-10 11:56:47","tags":[{"id":1,"name":"随笔","count":null}]}}
   ```

   +新建文章的1.和2.

4. 删除文章

   DELETE:http://127.0.0.1:8080/api/article/13

   ```json
   {"code":200,"msg":"success","data":null}
   ```

### 分类管理

1. list

   POST:http://127.0.0.1:8080/api/category/list?page=1&limit=20

   ```json
   {}
   ```

   ```json
   {"code":200,"msg":"success","data":{"total":4,"rows":[{"id":4,"name":"springboot"},{"id":3,"name":"心情"},{"id":2,"name":"随笔"},{"id":1,"name":"测试"}]}}
   ```

2. edit:

   PUT:http://127.0.0.1:8080/api/category

   ```json
   {"id":1,"name":"测试test"}
   ```

   ```json
   {"code":200,"msg":"success","data":null}
   ```

   +本类1.

3. delete

   DELETE:http://127.0.0.1:8080/api/category/2

   ```json
   {"code":200,"msg":"success","data":null}
   ```

   +本类1.

4. add

   POST:http://127.0.0.1:8080/api/category

   ```json
   {"name":"测试"}
   ```

   ```json
   {"code":200,"msg":"success","data":null}
   ```

5. 查询

   POST:http://127.0.0.1:8080/api/category/list?page=1&limit=20

   ```json
   {"name":"测试"}
   ```

   ```json
   {"code":200,"msg":"success","data":{"total":2,"rows":[{"id":5,"name":"测试"},{"id":1,"name":"测试test"}]}}
   ```

### 日志管理

1. POST:http://127.0.0.1:8080/api/log/list?page=1&limit=20

   ```json
   {"code":200,"msg":"success","data":{"total":20,"rows":[{"id":66,"username":"tycoding","operation":"新增分类","time":277,"method":"cn.tycoding.system.controller.CategoryController.save()","params":null,"ip":"127.0.0.1","createTime":"2020-05-10 13:01:34","location":"内网IP|0|0|内网IP|内网IP"},{"id":47,"username":"tycoding","operation":"新增文章","time":33,"method":"cn.tycoding.system.controller.ArticleController.save()","params":" sysArticle\"SysArticle(id=8, title=How to write an article?, cover=, author=tyco...","ip":"127.0.0.1","createTime":"2019-09-22 14:57:51","location":"内网IP|0|0|内网IP|内网IP"}]}}
   ```

   





















# Blog数据库

## MySQL:

### common

1. user:

   | Field     | Type         | Null | Key  | Default | Extra          |
   | --------- | ------------ | ---- | ---- | ------- | -------------- |
   | id        | bigint       | NO   | PRI  | NULL    | auto_increment |
   | username  | varchar(100) | NO   |      | NULL    |                |
   | password  | varchar(100) | NO   |      | NULL    |                |
   | salt      | varchar(200) | NO   |      | NULL    |                |
   | avatar    | varchar(200) | YES  |      | NULL    |                |
   | introduce | varchar(100) | YES  |      | NULL    |                |
   | role      | varchar(100) | NO   |      | NULL    |                |
   
2. channel_name

   | Fidld       | Type   | Null | Key  | Default | Extra          |
   | ----------- | ------ | ---- | ---- | ------- | -------------- |
   | id          | brgint | NO   | PRI  | NULL    | auto_increment |
   | content     |        |      |      | []()    |                |
   | date        |        |      |      |         |                |
   | user_id     |        |      |      |         |                |
   | user_name   |        |      |      |         |                |
   | user_avatar |        |      |      |         |                |

## MongoDB:

### Chat

1. channel:

   ```json
   {
       "_id",
       "channel_name",
       "create_date",
       "user"=[user_data.username]
   }
   ```

2. user_channel:

   ```json
   {
       "_id",
       "user_id":user_data.id,
       "channel":[channel.name]
   }
   ```

### Blog

1. article:

   ```json
   {
       "_id",
       "title",
       "cover",
       "author",
       "content",
       "contentMd",
       "category":[category.name],
       "tag":[tag.name],
       "state",
       "publish_time",
       "edit_time",
       "create_time",
   }
   ```
   
5. category:

   ```json
   {
       "_id",
       "name",
       "article":[article._id]
   }
   ```

6. tag:

   ```json
   {
       "_id",
       "name",
       "article":[article._id]
   }
   ```
   
4. comment:

   树结构:

   ```json
   {
       "user_avator",
       "user_id",
       "time",
       "content",
       "others",
   }
   ```

   ### 
### Card

1. user_id

   ```json
   {
       "_id",
       "key",
       "front_content_1",
       ...,
       "front_content_n",
       "back_content",
       "expire_date",
       "status"
   }
   ```

   