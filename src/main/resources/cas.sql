SET FOREIGN_KEY_CHECKS = 0;

drop database if exists cas;
create database if not exists cas;
use cas;

drop table if exists user;
-- 创建user表
create table `user` (
	`user_id` bigint unsigned not null auto_increment comment '用户ID',
	`username` varchar(20) not null comment '用户账号',
	`password` varchar(50) not null comment '用户密码',
	`sn` varchar(20) comment '学工号',
	`sn_password` varchar(50) comment '学工号密码',
	`phone` char(11) not null comment '电话号码',
	`email` varchar(30) comment '用户邮箱',
	`level` bit(3) default 3 comment '用户权限等级',
	`head_img_path` varchar(255) default '/home/rekord/cas/img/headImg/default/default.jpg' comment '用户头像图片路径',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`user_id`) using BTREE,
	unique index (`username`) using Btree,
	unique index (`sn`) using btree
) ENGINE=InnoDB auto_increment=125 DEFAULT charset=utf8;

drop table if exists manager1;
-- 创建manager1表
create table `manager1` (
	`manager_id` int(11) unsigned not null auto_increment comment '一级管理员ID',
	`user_id` bigint unsigned not null comment '用户ID',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',        
	primary key (`manager_id`) using BTREE,
	constraint FK_MANAGER1_USER_ID foreign key(`user_id`) references user(`user_id`)
) engine=InnoDB auto_increment=125 default charset=utf8;

drop table if exists manager2;
-- 创建manager2表
create table `manager2` (
	`manager_id` int(11) unsigned not null auto_increment comment '二级管理员ID',
	`user_id` bigint unsigned not null comment '用户ID',
	`parent` int(11) unsigned not null comment '权限授予者',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`manager_id`) using BTREE,
	constraint FK_MANAGER2_USER_ID foreign key(`user_id`) references user(`user_id`) on delete cascade,
	constraint FK_MANAGER2_PARENT foreign key(`parent`) references manager1(`manager_id`) on delete cascade
) engine=InnoDB auto_increment=125 default charset=utf8;

drop table if exists authority_record;
-- 创建权限记录表
create table `authority_record` (
	`authority_record_id` bigint unsigned not null auto_increment comment '权限记录ID',
	`from_user_id` bigint unsigned not null comment '权限管理主动方',
	`to_user_id` bigint unsigned not null comment '权限管理被动方',
	`action` bit(2) not null comment '权限管理操作, 0表示撤销，1表示授予',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`authority_record_id`) using btree,
	constraint FK_AUTHORITY_RECORD_FROM_ID foreign key(`from_user_id`) references user(`user_id`),
	constraint FK_AUTHORITY_RECORD_TO_ID foreign key(`to_user_id`) references user(`user_id`)
) ENGINE=InnoDB default charset=utf8;



drop table if exists semester;
-- 创建学期表
create table `semester` (
	`semester_id` int(11) unsigned not null auto_increment comment '学期ID',
	`semester_name` char(9) not null comment '学期',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`semester_id`) using btree,
	unique index (`semester_name`) using btree,
	constraint CHK_SEMESTER_NAME check (char_length(semester_name) = 9) 
) engine=InnoDB auto_increment=100 default charset=utf8;

  
    
drop table if exists activity;
-- 创建活动表
create table `activity` (
	`act_id` bigint unsigned not null auto_increment comment '活动ID',
	`act_name` varchar(255) unique not null comment '活动名',
	`act_description` varchar(255) comment '活动描述',
	`act_img_path` varchar(255) default '/home/rekord/cas/img/activityImg/default/default.jpg' comment '活动预览图',
	`act_reg_count` int(11) not null default 0 comment '已报名人数',
	`act_reg_max_count` int(11) not null comment '报名人数上限',
	`act_reg_start_date` datetime comment '报名开始时间',
	`act_reg_end_date` datetime comment '报名截止时间',
	`act_time` datetime comment '活动时间',
	`act_place` varchar(255) comment '活动地点',
	`act_category` char(2) default '德育' comment '活动类别',
	`semester_id` int(11) unsigned not null comment '所属学期',
	`act_punch_start_time` datetime comment '活动打卡开始时间',
	`act_punch_end_time` datetime comment '活动打卡结束时间',
	`act_punch_longitude` decimal(17, 6) comment '活动打卡经度',
	`act_punch_latitude` decimal(17, 6) comment '活动打卡纬度',
	`act_is_publish` bit(3) default 0 comment '活动是否成功发布',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`act_id`) using btree,
	unique index (`act_name`) using BTREE,
	constraint FK_ACTIVITY_SEMESTER_ID foreign key(`semester_id`) references semester(`semester_id`), 
	constraint FK_ACT_REG_COUNT check (act_reg_count >= 0 and act_reg_max_count > 0)
) ENGINE=InnoDB auto_increment=500 DEFAULT charset=utf8;

drop table if exists par_activity;
-- 创建参与活动表
create table `par_activity` (
	`id` bigint unsigned not null auto_increment comment '参与活动ID',
	`user_id` bigint unsigned not null comment '用户ID',
	`act_id` bigint unsigned not null comment '活动ID',
	`reg_time` datetime DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP(0) comment '报名时间',
	`reg_number` int(11) not null comment '报名号',
	`grade` tinyint comment '活动所获分数',
	`explanation` varchar(255) not null default '普通参与' comment '加分说明',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`id`) using btree,
	constraint FK_PAR_ACTIVITY_ACT_ID foreign key(`act_id`) references activity(`act_id`) on delete cascade,
	constraint FK_PAR_ACTIVITY_USER_ID foreign key(`user_id`) references user(`user_id`) on delete cascade
) ENGINE=InnoDB DEFAULT charset=utf8;
    
drop table if exists man_activity;
-- 创建管理活动表
create table `man_activity` (
	`id` bigint unsigned not null auto_increment comment '管理活动ID',
	`user_id` bigint unsigned not null comment '用户ID',
	`act_id` bigint unsigned not null comment '活动ID',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key (`id`) using btree,
	constraint FK_MAN_ACTIVITY_USER_ID foreign key(`user_id`) references user(`user_id`) on delete cascade,
	constraint FK_MAN_ACTIVITY_ACT_ID foreign key(`act_id`) references activity(`act_id`) on delete cascade
) ENGINE=InnoDB DEFAULT charset=utf8;
    


drop table if exists award_certificate;
-- 创建获奖证书表
create table `award_certificate` (
	`ac_id` bigint unsigned not null auto_increment comment '获奖证书id',
	`name` varchar(255) not null comment'证书名',
	`user_id` bigint unsigned not null comment '用户ID',
	`is_valid` bit(2) default 0 comment '是否纳入综测',
	`category` char(2) default '德育' comment '证书类别', 
	`grade` tinyint comment '加分',
	`explanation` varchar(255) not null comment '加分说明',
	`comment` varchar(255) not null default '' comment '证书备注',
	`img_path` varchar(255) default '/home/rekord/cas/img/acImg/default/default.jpg' comment '图片路径',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	`semester_id` int unsigned not null comment '学期ID',
	primary key (`ac_id`) using btree,
	unique index (`name`) using btree,
	constraint FK_AC_USER_ID foreign key(`user_id`) references user(`user_id`) on delete cascade,
	constraint FK_AC_SEMESTER_ID foreign key(`semester_id`) references semester(`semester_id`)
) ENGINE=InnoDB DEFAULT charset=utf8;



drop table if exists message;
-- 创建消息表
create table `message` (
	`message_id` bigint unsigned not null auto_increment comment '通知ID',
	`from_user_id` bigint unsigned not null comment '发送方ID',
	`to_user_id` bigint unsigned not null comment '接收方ID',
	`content` varchar(255) not null comment '通知内容',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	`status` bit(2) default 0 comment '状态', 
	primary key (`message_id`) using btree,
	constraint FK_MESSAGE_FROM_USER_ID foreign key(`from_user_id`) references user(`user_id`) on delete cascade,
	constraint FK_MESSAGE_TO_USER_ID foreign key(`to_user_id`) references user(`user_id`) on delete cascade
) ENGINE=InnoDB auto_increment=500 default charset=utf8;
    


drop table if exists application;
-- 创建申请表
create table `application` (
	`application_id` bigint unsigned not null auto_increment comment '申请ID',
	`application_from_id` bigint unsigned not null comment '申请方ID',
	`application_to_id` bigint unsigned not null comment '审批方ID',
	`category` char(6) not null comment '申请类别，如证书申请、活动发布申请',
	`status` bit(3) default 0 comment '0为未回复，1为通过，2为驳回',
	`link_id` bigint unsigned not null comment '申请链接，可以根据类别确定是哪种类型的申请',
	`create_time` datetime not null default current_timestamp comment '申请创建时间',
	`reply_time` datetime default null comment '申请回复时间',
	`expire_day` int unsigned not null default 5 comment '过期天数，发起后如果一定时间得不到回复则自动过期',
	`comment` varchar(255) comment '备注信息',
	`update_time` datetime default current_timestamp on update CURRENT_TIMESTAMP(0) comment '更新时间',
	primary key(`application_id`) using btree,
	constraint FK_APPLICATION_FROM_ID foreign key(`application_from_id`) references user(`user_id`) on delete cascade,
	constraint FK_APPLICATION_TO_ID foreign key(`application_to_id`) references user(`user_id`) on delete cascade
) ENGINE=InnoDB auto_increment=500 default charset=utf8;
    


DROP TABLE IF EXISTS `version`;
-- 创建版本表
CREATE TABLE `version` (
	`version_id` int unsigned NOT NULL AUTO_INCREMENT comment '版本ID',
	`version_name` varchar(255) NOT NULL comment '版本名',
	`apk_path` varchar(255) NOT NULL comment 'apk路径',
	`info` varchar(255) DEFAULT NULL comment '版本信息',
	`create_time` datetime default current_timestamp comment '创建时间',
	`update_time` datetime DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
	PRIMARY KEY (`version_id`) USING BTREE,
	UNIQUE INDEX (`version_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 default charset=utf8;


SET FOREIGN_KEY_CHECKS = 1;