use `common`;

set names utf8;
drop table if exists `user`;
create table `user`
(
    `id`        bigint(20)   not null auto_increment,
    `username`  varchar(100) not null,
    `password`  varchar(100) not null,
    `salt`      varchar(200) default null,
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

drop table if exists `comment`;
create table `comment`
(
    `id`            bigint(20)   not null auto_increment,
    `article_title` varchar(100) default null,
    `article_id`    varchar(100) default null,
    `reply_id`      varchar(100) default null,
    `username`      varchar(100) default null,
    `avatar`        varchar(200) default null,
    `date`          datetime     not null,
    `content`       varchar(300) not null,
    primary key (`id`)
)
    engine = InnoDB
    auto_increment = 1
    default charset = utf8



