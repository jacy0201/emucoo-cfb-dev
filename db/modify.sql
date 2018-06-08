-- t_task 添加字段 start_time 、end_time
alter table t_task add start_time varchar(50) default NULL COMMENT '开始时间： HH:mm';
alter table t_task add end_time varchar(50) default NULL COMMENT '结束时间： HH:mm';
