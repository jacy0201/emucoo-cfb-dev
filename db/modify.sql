-- t_task 添加字段 start_time 、end_time
alter table t_task add start_time varchar(50) default NULL COMMENT '开始时间： HH:mm';
alter table t_task add end_time varchar(50) default NULL COMMENT '结束时间： HH:mm';

-- sys_user 添加字段 is_repairer
alter table sys_user add is_repairer bit(1) default NULL COMMENT '是否是维修专员:1-是；0-否';
