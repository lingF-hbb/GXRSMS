-- 用户表
drop table if exists user;
create table user
(
    id          bigint(11)     not null primary key auto_increment comment 'id',
    username    varchar(11)    not null comment '用户名',
    password    varchar(100)   not null comment '密码',
    icon        varchar(200) comment '头像' default '/static/imgs/icon/user_default_icon.jpg',
    nick_name   varchar(50) comment '昵称',
    address     varchar(100) comment '地址',
    phone       varchar(15) comment '联系方式',
    email       varchar(50) comment '电子邮箱',
    is_admin    tinyint signed            default '0' comment '是否管理员',
    status      tinyint signed not null   default 1 comment '0-停用,1-正常',
    create_time timestamp      null       default '0000-00-00 00:00:00' comment '创建时间',
    update_time timestamp      null       default current_timestamp on update current_timestamp comment '修改时间'
) charset = utf8mb4 comment '用户表';
create trigger create_time
    before insert
    on user
    for each row set new.create_time = now();
INSERT INTO user ( username, password, nick_name, phone, email, is_admin)
VALUES ( 'admin', 'hWrqia1QnxYyhKu3VXnc/A==', '张三', '15638249533', '595952187@qq.com', '1');

-- 档案管理表
drop table if exists files;
create table files
(
    id          bigint(11)   not null primary key auto_increment comment 'id',
    file_name   varchar(100) not null comment '文件名',
    file_desc   text         not null comment '文件描述',
    file_path   varchar(100) not null comment '文件路径',
    create_time datetime     not null default '0000-00-00 00:00:00' comment '创建时间',
    update_time timestamp    not null default current_timestamp on update current_timestamp comment '修改时间'
) charset = utf8mb4 comment '档案管理';
create trigger files_create
    before insert
    on files
    for each row set new.create_time = now();

-- 部门表
drop table if exists dept;
create table dept
(
    id          bigint      not null primary key auto_increment,
    dept_no     varchar(20) not null comment '部门编号',
    dept_name   varchar(20) not null comment '部门名称',
    create_time timestamp   not null default '0000-00-00 00:00:00' comment '创建时间',
    update_time timestamp            default current_timestamp on update current_timestamp comment '修改时间'
) charset = utf8mb4 comment '部门表';
create trigger dept_create
    before insert
    on dept
    for each row set new.create_time = now();
insert into dept(dept_no, dept_name) value ('D0001', '宿管部');



-- 员工信息表
drop table if exists employee;
create table employee
(
    id          bigint primary key auto_increment,
    emp_no      varchar(20) not null comment '员工编号',
    name        varchar(20) not null comment '姓名',
    gender      varchar(10) comment '性别',
    address     varchar(100) comment '居住地址',
    phone   varchar(20) not null comment '联系方式',
    email       varchar(50) comment '邮箱',
    dept_no     varchar(20) not null comment '所属部门',
    work_status varchar(50) comment '工作状态: 0-离职 1-在职',
    create_time timestamp   not null default '0000-00-00 00:00:00' comment '创建时间',
    update_time timestamp   not null default current_timestamp on update current_timestamp comment '修改时间'
) charset = utf8mb4 comment '员工信息表';
create trigger emp_create
    before insert
    on employee
    for each row set new.create_time = now();
insert into employee(emp_no, name, gender, address, phone, email, dept_no)
value ('E000001','张三','男','河南省郑州市','12562365748','25481@qq.com','D0001');

drop table if exists salary;
create table salary
(
    id          bigint primary key auto_increment,
    emp_no      varchar(20) not null comment '员工编号',
    jiben      decimal(17, 2)       default '0.00' comment '基本薪资',
    jintie      decimal(17, 2)       default '0.00' comment '津贴',
    keshi      decimal(17, 2)       default '0.00' comment '课时费',
    shebao      decimal(17, 2)       default '0.00' comment '缴纳社保',
    salary      decimal(17, 2)       default '0.00' comment '应发薪资',
    extend_time varchar(50)   not null comment '本月发薪时间',
    finished    varchar(50) not null comment '发放状态',
    create_time timestamp   not null default '0000-00-00 00:00:00' comment '创建时间',
    update_time timestamp            default current_timestamp on update current_timestamp comment '修改时间'
) charset = utf8mb4 comment '薪酬管理';
create trigger salary_create
    before insert
    on salary
    for each row set new.create_time = now();
insert into salary(emp_no, jiben, salary, extend_time, finished)
value ('E000001', '1000.00','1000.00', '2020-05-04', '已完成');


drop table if exists sanction;
create table sanction(
    id bigint primary key auto_increment comment 'id',
    emp_no varchar(20) not null comment '员工编号',
    detail varchar(20) comment '奖惩明细',
    reason varchar(200) comment '奖惩原因',
    create_time timestamp   not null default '0000-00-00 00:00:00' comment '创建时间',
    update_time timestamp            default current_timestamp on update current_timestamp comment '修改时间'
) charset utf8mb4 comment '奖惩管理';
create trigger sanction_create
    before insert
    on sanction
    for each row set new.create_time = now();
insert into sanction (emp_no, detail, reason) value ('E000001', '+1000.00', '加班费');















