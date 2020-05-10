set names utf8;
drop table if exists `user`;
create table `user`
(
    `id`        bigint(20)   not null auto_increment,
    `username`  varchar(100) not null,
    `password`  varchar(100) not null,
    `salt`      varchar(200) not null,
    `avatar`    varchar(200) default null,
    `introduce` varchar(100) default null,
    `role`      varchar(100) default 'user',
    primary key (`id`)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8;

drop table if exists `channel_name`;
create table `channel_name`
(
    `id`          bigint(20)   not null auto_increment,
    `content`     varchar(100) not null,
    `date`        datetime     not null,
    `user_id`     bigint(20)   not null,
    `user_name`   varchar(100) not null,
    `user_avatar` varchar(200) default null,
    primary key (`id`)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8;


