drop database spring_test;
create database spring_test;
use spring_test;
/*用户*/
drop table if exists user;
create table user
(
	ID bigint auto_increment
		primary key,
	ACCOUNT_ID varchar(100),
	NAME varchar(50) null,
	TOKEN varchar(36) null,
	GMT_CREATE bigint null,
	GMT_MODIFIED bigint null,
	bio varchar(256) null,
	avatar_url varchar(255) null
);
/*问题*/
drop table if exists question;
create table question
(
	id bigint auto_increment
		primary key,
	title varchar(50) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator bigint not null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
	tag varchar(256) null,
/*外键约束：question creator对应user id*/
	foreign key(creator) references user(id)
);
/*评论*/
drop table if exists comment;
create table comment
(
	id bigint auto_increment
		primary key,
	parent_id bigint not null comment '被评论的帖子或者是评论的id',
	type int not null comment '是一级回复or二级回复',
	commentator bigint not null comment '评论者id',
	gmt_create bigint not null,
	gmt_modified bigint not null,
	like_count int default 0 null,
	comment_count int default 0 null comment '二级评论数',
	content varchar(255) not null
);
/*外键：评论者---用户ID*/
alter table comment add constraint fk_reference_2 foreign key(commentator) references user(id) on delete restrict on update restrict;


/*通知*/
create table notification
(
	id bigint auto_increment,
	notifier bigint not null comment '通知者',
	receiver bigint not null comment '接收者',
	outerId bigint not null comment '回复的问题/评论的ID',
	type int not null comment '回复的是评论或问题',
	gmt_create bigint null,
	status int default 0 null comment '状态：0：未读，1：已读',
	constraint notification_pk
		primary key (id)
);
/*外键：创建者---用户，失败不知原因。。。
alter table notification add constraint fk_reference_2 foreign key(notifier) references user(id) on delete restrict on update restrict;*/







