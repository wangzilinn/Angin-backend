## IM用户操作流程

主动:

1. 用户创建一个channel
2. 用户为channel添加其他用户
3. 多个用户通过channel聊天

主动:

1. 用户搜索channel
2. 用户请求加入channel
3. 用户进入channel聊天

被动:

1. 用户被拉进一个channel
2. 同意后, 用户进入channel聊天

## IM用户储存策略

使用一个collection储存每个用户的数据

每个document内容有:

1. 用户名
2. channels(list)

## IM消息储存策略

不使用以用户为中心的储存策略, 使用以channel为中心的储存策略, 每个一对一或多对多的channel使用一个collection