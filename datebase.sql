drop database spring_test;
create database spring_test;
use spring_test;
/*用户*/
drop table if exists user;
create table user
(
	ID int,
	ACCOUNT_ID varchar(100) primary key,
	NAME varchar(50) null,
	TOKEN varchar(36) null,
	GMT_CREATE bigint null,
	GMT_MODIFIED bigint null,
	bio varchar(256) null,
	avatar_url varchar(100) null
);
/*发帖*/
drop table if exists question;
create table question
(
	id int auto_increment
		primary key,
	title varchar(50) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator varchar(100) not null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
	tag varchar(256) null,
/*外键约束：question creator对应user id,但这里的*/
	foreign key(creator) references user(ACCOUNT_ID)
);
/*评论*/
create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int not null comment '是一级回复or二级回复',
	commentator varchar(11) not null,
	gmt_create bigint not null,
	gmt_modified bigint not null,
	like_count bigint default 0 null,
	content varchar(255) not null,
	constraint comment_pk
		primary key (id)
);






