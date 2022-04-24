SET FOREIGN_KEY_CHECKS = 0;

create database if not exists cas;
use cas;

drop table if exists user;
-- 创建user表
create table if not exists user
	(
		`user_id` int(11) not null auto_increment comment '用户ID',
        `username` varchar(20) not null comment '用户账号',
        `password` varchar(20) not null comment '用户密码',
        `sn` varchar(20) unique comment '学工号',
        `sn_password` varchar(30) comment '学工号密码',
        `phone` varchar(11) not null comment '电话号码',
        `email` varchar(20) comment '用户邮箱',
        `level` enum('0','1','2','3') default '3' comment '用户权限等级',
        `head_img_path` varchar(255) default '/home/rekord/cas/img/headImg/default/default.jpg' comment '用户头像图片路径',
		primary key(`user_id`) using BTREE,
        unique index `username`(`username`) using Btree
	) ENGINE=InnoDB auto_increment=125 DEFAULT charset=utf8;


drop table if exists manager_1;
-- 创建manager_1表
create table if not exists manager_1
	(
		`manager_id` int(11) not null auto_increment comment '一级管理员ID',
        `user_id` int(11) not null comment '用户ID',
		primary key(`manager_id`) using BTREE,
        constraint manager1_user_id foreign key(`user_id`) references user(`user_id`)
    );
    

drop table if exists manager_2;
-- 创建manager_2表
create table if not exists manager_2 
	(
		`manager_id` int(11) not null auto_increment comment '二级管理员ID',
        `user_id` int(11) not null comment '用户ID',
        `parent` int(11) not null comment '权限授予者',
        primary key(`manager_id`) using BTREE,
        constraint manager2_user_id foreign key(`user_id`) references user(`user_id`) on delete cascade,
		constraint manager2_parent foreign key(`parent`) references manager_1(`manager_id`) on delete cascade
    );
      
      
-- drop table if exists head_img;
-- -- 创建head_img表
-- CREATE TABLE if not exists `head_img`  (
--   `hi_id` int NOT NULL AUTO_INCREMENT,
--   `user_id` int UNSIGNED NOT NULL,
--   `head_path` varchar(255) default '/header_img/default.jpg' comment '用户头像图片路径',
--   PRIMARY KEY (`id`) USING BTREE,
--   UNIQUE INDEX `user_id`(`user_id`) USING BTREE,
--   CONSTRAINT hi_user_id FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
-- ) ENGINE = InnoDB AUTO_INCREMENT = 265 default charset=utf8;


drop table if exists activity;
-- 创建活动表
create table if not exists activity 
	(
		act_id int(11) not null auto_increment comment '活动ID',
        act_name varchar(255) unique not null comment '活动名',
        act_description varchar(255) comment '活动描述',
        act_img_path varchar(255) default '/home/rekord/cas/img/actImg/default/default.jpg' comment '活动预览图',
        act_reg_count int(11) not null default 0 comment '已报名人数',
        act_reg_max_count int(11) not null comment '报名人数上限',
        act_reg_start_date date comment '报名开始时间',
        act_reg_end_date date comment '报名截止时间',
        act_time datetime(0) comment '活动时间',
        act_place varchar(255) comment '活动地点',
        act_category enum('劳育','体育','德育','智育') default '德育' comment '活动类别',
        primary key(`act_id`),
        unique index (`act_name`) using BTREE
	) ENGINE=InnoDB auto_increment=500 DEFAULT charset=utf8;


drop table if exists award_certificate;
-- 创建获奖证书表
create table if not exists award_certificate
	(
		`ac_id` int(11) not null auto_increment comment '获奖证书id',
        `user_id` int(11) not null comment '用户ID',
        `name` varchar(255) not null comment'证书名',
        `is_valid` enum('0', '1') default '0' comment '是否纳入综测',
        `grade` int comment '加分',
        `category` enum('劳育','体育','德育','智育') default '德育' comment '证书类别', 
        `explanation` varchar(80) not null comment '加分说明',
        `comment` varchar(255) comment '证书备注',
        `img_path` varchar(255) default '/home/rekord/cas/img/ACImg/default/default.jpg' comment '图片路径',
        `update_date` datetime DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
        primary key(`ac_id`),
        constraint ac_user_id foreign key(`user_id`) references user(`user_id`) on delete cascade
	) ENGINE=InnoDB DEFAULT charset=utf8;


drop table if exists message;
-- 创建消息表
create table if not exists message 
	(
		`message_id` int(11) not null auto_increment comment '通知ID',
        `from_user_id` int(11) not null comment '发送方ID',
        `to_user_id` int(11) not null comment '接收方ID',
 		`content` varchar(255) not null comment '通知内容',
        `create_time` datetime(0) not null comment '创建时间',
        `status` enum('0','1') not null default '0' comment '状态', 
        primary key(`message_id`),
        constraint message_from_user_id foreign key(`from_user_id`) references user(`user_id`) on delete cascade,
        constraint message_to_user_id foreign key(`to_user_id`) references user(`user_id`) on delete cascade
	) ENGINE=InnoDB auto_increment=500 default charset=utf8;
    
    
drop table if exists application;
-- 创建申请表
create table if not exists application
	(
		`application_id` int(11) not null auto_increment comment '申请ID',
        `application_from_id` int(11) not null comment '申请方ID',
        `application_to_id` int(11) not null comment '审批方ID',
        `category` varchar(80) not null comment '申请类别，可以是证书申请，也可以是活动发布申请',
		`status` enum('0', '1', '2') default '0' comment '0为未回复，1为通过，2为驳回',
        `link_id` int(11) not null comment '申请链接，可以根据类别确定是哪种类型的申请',
        `create_time` datetime not null default current_timestamp comment '申请创建时间',
        `reply_time` datetime comment '申请回复时间',
        `expire_day` int not null default 5 comment '过期天数，发起后如果一定时间得不到回复则自动过期',
        `comment` varchar(255) comment '备注信息',
        primary key(`application_id`),
        constraint application_from_id foreign key(`application_from_id`) references user(`user_id`) on delete cascade,
        constraint application_to_id foreign key(`application_to_id`) references user(`user_id`) on delete cascade
	) ENGINE=InnoDB auto_increment=500 default charset=utf8;
    

-- 查询所有发起的申请
select * from application 
where application_from_id = 125;
-- 查询所有收到的申请
select * from application
where application_to_id = 125;
-- 查看申请内容

DROP TABLE IF EXISTS `version`;
-- 创建版本表
CREATE TABLE if not exists `version`  (
  `version_id` int NOT NULL AUTO_INCREMENT comment '版本ID',
  `version_name` varchar(255) NOT NULL comment '版本名',
  `apk_path` varchar(255) NOT NULL comment 'apk路径',
  `update_time` datetime DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `info` varchar(255) DEFAULT NULL comment '版本信息',
  PRIMARY KEY (`version_id`) USING BTREE,
  UNIQUE INDEX `vname`(`version_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 default charset=utf8;



drop table if exists par_activity;
-- 创建参与活动表
create table if not exists par_activity 
	(
		`id` int(11) not null auto_increment comment '参与活动ID',
        `user_id` INT(11) not null comment '用户ID',
        `act_id` int(11) not null comment '活动ID',
        `reg_number` int not null comment '报名号',
        `grade` int comment '活动所获分数',
        `explanation` varchar(255) not null comment '加分说明',
        primary key(`id`),
        constraint par_activity_act_id foreign key(`act_id`) references activity(`act_id`) on delete cascade,
        constraint par_activiry_user_id foreign key(`user_id`) references user(`user_id`) on delete cascade
	) ENGINE=InnoDB DEFAULT charset=utf8;
    
    
drop table if exists man_activity;
-- 创建管理活动表
create table if not exists man_activity 
	(
		`id` int(11) not null auto_increment comment '管理活动ID',
        `user_id` int(11) not null comment '用户ID',
        `act_id` int(11) not null comment '活动ID',
        primary key(`id`),
        constraint man_activity_user_id foreign key(`user_id`) references user(`user_id`) on delete cascade,
        constraint man_activity_act_id foreign key(`act_id`) references activity(`act_id`) on delete cascade
	) ENGINE=InnoDB DEFAULT charset=utf8;

    







-- 设定超级管理员
insert into user(`username`, `password`, `phone`, `level`) values("root", "123456", "18273447586", '0');
-- 设定一级管理员
insert into user(`username`, `password`, `phone`, `level`) values("manager1", "123456", "18273447586", "1");
insert into manager_1(`user_id`) values(126);
-- 注册
insert into user(`username`, `password`, `phone`) values("rekord", "011010", "19330381990");
-- 绑定学工号
update user 
set sn = "201903130218", sn_password = "17573401869@yiCAO"
where username = "rekord";
-- 绑定邮箱
update user
set email = "wildrekord@gmail.com"
where username = "rekord";

     
-- 查询所有用户
select * from user;
-- 根据账号密码登录
select * from user where username = "rekord" and password = "011010";
-- 根据学号密码登录（需要绑定）alter
select * from user where sn = "201903130218" and sn_password = "17573401869@yiCAO";
-- 根据手机号登录
select * from user where phone = "19330381990";

-- 删除账号
-- delete from user where `username` = "root";

        
        
-- 删除活动
delete from activity 
where `act_id` = "500";

-- 发布活动
insert into activity(`act_name`, `act_description`, `act_reg_max_count`, `act_reg_start_date`, `act_reg_end_date`, `act_time`, `act_place`, `act_category`)
values ("计通学院凌云杯第一场篮球赛观众招募", "为了丰富学生的课余生活，提高学生的综合素质，培养团队精神，增强集体凝聚力，展现学生积极向上的风貌，2022年3月，计通学院将举行凌云杯篮球比赛", 
	80, '2022-02-27', '2022-02-28', '2022-03-02 15:00', "云塘校区体育馆二楼", "德育");
insert into activity(`act_name`, `act_description`, `act_reg_max_count`, `act_reg_start_date`, `act_reg_end_date`, `act_time`, `act_place`, `act_category`)
values ("“互联网+”创新大赛院赛报名", "为了丰富学生的课余生活，提高学生的综合素质，培养团队精神，增强集体凝聚力，展现学生积极向上的风貌，2022年3月，计通学院将举行“互联网+”创新大赛院赛报名", 
	100, '2022-02-24', '2022-02-28', '2022-03-02 15:00', "", "德育");
insert into activity(`act_name`, `act_description`, `act_reg_max_count`, `act_reg_start_date`, `act_reg_end_date`, `act_time`, `act_place`, `act_category`)
values ("测试活动2", "为了丰富学生的课余生活，提高学生的综合素质，培养团队精神，增强集体凝聚力，展现学生积极向上的风貌，2022年3月，计通学院将举行“互联网+”创新大赛院赛报名", 
	100, '2022-02-24', '2022-04-28', '2022-04-29 15:00', "", "德育");
insert into activity(`act_name`, `act_description`, `act_reg_max_count`, `act_reg_start_date`, `act_reg_end_date`, `act_time`, `act_place`, `act_category`)
values ("测试活动3", "为了丰富学生的课余生活，提高学生的综合素质，培养团队精神，增强集体凝聚力，展现学生积极向上的风貌，2022年3月，计通学院将举行“互联网+”创新大赛院赛报名", 
	100, '2022-04-22', '2022-04-23', '2022-04-24 15:00', "", "德育");    

-- 全部查询
select * from activity;
-- 分页查询所有正在进行报名的活动 
select * from activity
where datediff(current_date, act_reg_start_date) >= 0
order by `act_reg_start_date` desc
limit 0,4;

-- 查询所有已经结束（并非报名结束）的活动
select * from activity
where datediff(current_timestamp, act_time) < 0
order by `act_reg_end_date` desc
limit 0,4;


-- 如果结果大于0,则不执行插入语句
select count(`id`) from par_activity
where par_activity.user_id = 125 and par_activity.act_id = 500;
-- 报名活动
insert into par_activity(`user_id`, `act_id`, `reg_number`, `explanation`)
values(125, 503, 1, "普通参与");

-- 查询所有已报名的活动
select activity.* from par_activity, activity
where par_activity.user_id = 125 and par_activity.act_id = activity.act_id
order by `act_reg_start_date` desc
limit 0,4;


-- 上传证书
insert into award_certificate(`user_id`, `name`, `is_valid`, `explanation`)
values("125", "程序设计大赛", "1", "校级二等奖");

-- 查询所有证书
select * from award_certificate;

-- 查询特定用户下的所有证书
select award_certificate.* from `award_certificate`, `user`
where user.`user_id` = 125 and user.user_id = award_certificate.user_id;


-- 查询par_activity
select * from par_activity;

-- 查询特定用户的综测细则 （活动）
select activity.act_name, par_activity.grade, activity.act_category, par_activity.explanation 
from `par_activity`, `user`, `activity`
where user.username = "rekord" and user.user_id = par_activity.user_id and par_activity.act_id = activity.act_id;
-- 证书纳入
select award_certificate.name, award_certificate.grade, award_certificate.category, award_certificate.explanation from `award_certificate`, `user`
where user.`user_id` = 125 and user.user_id = award_certificate.user_id and
is_valid = '1';


-- 查询今日活动提醒
select * from par_activity, activity
where `user_id` = 125 and par_activity.act_id = activity.act_id and
datediff(current_date(), activity.act_time) = 0; 


-- 查询特定用户下的所有证书
select award_certificate.* from `award_certificate`, `user`
where user.`username` = "rekord" and user.user_id = award_certificate.user_id and award_certificate.user_id;
 
-- 成功发布活动
insert into man_activity(`user_id`, `act_id`)
values(125, 500);
-- 查询特定用户管理的所有活动
select man_activity.* from `man_activity`
where user_id = 125;



SET FOREIGN_KEY_CHECKS = 1;