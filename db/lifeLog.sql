use
    `common`;

set names utf8;
drop table if exists `lifelog`;
create table `lifelog`
(
    `id`        bigint(20)   not null auto_increment,
    `type_id`   varchar(100) not null,
    `type_name` varchar(100) not null,
    `date`      DATETIME default null,
    primary key (`id`)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8;


set names utf8;
drop table if exists `lifelog_summary`;
create table `lifelog_summary`
(
    `id`           bigint(20)   not null auto_increment,
    `type_name`    varchar(100) not null,
    `last_date`    DATETIME   default null,
    `duration`     bigint(20) default null,
    `max_duration` bigint(20) default null,
    primary key (`id`)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8;

select * from lifelog_summary;

select * from lifelog;

# 创建summary：

insert into lifelog_summary (type_name)
values (
        'exercise'
       );

