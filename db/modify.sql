-- t_task 添加字段 start_time 、end_time
alter table t_task add start_time varchar(50) default NULL COMMENT '开始时间： HH:mm';
alter table t_task add end_time varchar(50) default NULL COMMENT '结束时间： HH:mm';

-- sys_user 添加字段 is_repairer
alter table sys_user add is_repairer bit(1) default NULL COMMENT '是否是维修专员:1-是；0-否';

-- t_operate_data_for_work
alter table t_operate_data_for_work add column

  `name` varchar(100) DEFAULT NULL COMMENT '操作项名称',
  `num_need` bit(1) DEFAULT NULL COMMENT '是否显示数字类型项',
  `img_need` bit(1) DEFAULT NULL COMMENT '是否需要照片',
  `img_option_max_count` int(11) DEFAULT NULL COMMENT '最大照片数量',
  `img_option_from_album` bit(1) DEFAULT NULL COMMENT '是否可用相册',
  `img_example_id` bigint(20) DEFAULT NULL COMMENT '示例图片',
  `txt_need` bit(1) DEFAULT NULL COMMENT '是否显示文本项',
  `txt_option_name` varchar(50) DEFAULT NULL COMMENT '文本项名称',
  `txt_option_description` varchar(255) DEFAULT NULL COMMENT '文本项描述',
  `pre_score` varchar(10) DEFAULT NULL COMMENT '预设分值',
  `pre_weight` varchar(10) DEFAULT NULL COMMENT '预设权重'
  ;
