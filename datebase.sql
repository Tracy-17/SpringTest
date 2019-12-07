create datebase spring_test;

create table user
(
	ID int auto_increment
		primary key,
	ACCOUNT_ID varchar(100) null,
	NAME varchar(50) null,
	TOKEN varchar(36) null,
	GMT_CREATE bigint null,
	GMT_MODIFIED bigint null,
	bio varchar(256) null
);
create table question
(
	id int auto_increment
		primary key,
	title varchar(50) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator int null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
	tag varchar(256) null
);


