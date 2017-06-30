-- drop table tb_system
-- truncate table tb_system
-- select * from tb_system limit 100;
create table tb_system (
 id varchar(32) primary key,
 ename varchar(64),
 show_name varchar(64),
 company_name varchar(32),
 url varchar(128) unique comment 'http://abc.dd.com/system_one/',
 memo varchar(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统';
insert into tb_system(id, ename, show_name, company_name, url, memo)
select '1', 'self-system', 'self-system', 'self-company', 'http://localhost:6666/system_one/', 'self-system' ;

-- drop table tb_module
-- truncate table tb_module
-- select * from tb_module limit 100;
create table tb_module (
 id varchar(32) primary key,
 parent_id varchar(32),
 system_id varchar(32),
 ename varchar(64),
 show_name varchar(64),
 url varchar(128),
 memo varchar(1024),
 unique key(system_id, ename),
 unique key(system_id, url)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块';

insert into tb_module(id, parent_id, system_id, ename, show_name, memo)
select replace(uuid(),'-',''), '0', '1', 'index', '首页', '' from dual;

insert into tb_module(id, parent_id, system_id, ename, show_name, memo)
select replace(uuid(),'-',''), m.id, m.system_id, 'index-1', '', '' from tb_module m where m.system_id = '1' and m.ename = 'index' union
select replace(uuid(),'-',''), m.id, m.system_id, 'index-2', '', '' from tb_module m where m.system_id = '1' and m.ename = 'index' union
select replace(uuid(),'-',''), m.id, m.system_id, 'index-3', '', '' from tb_module m where m.system_id = '1' and m.ename = 'index' union
select replace(uuid(),'-',''), m.id, m.system_id, 'index-4', '', '' from tb_module m where m.system_id = '1' and m.ename = 'index'
;

-- drop table tb_module_view
-- truncate table tb_module_view
-- select * from tb_module_view limit 100;
create table tb_module_view (
 id varchar(32) primary key,
 module_id varchar(32),
 ename varchar(64),
 show_name varchar(64),
 row_num int,
 col_num int,
 `row` int,
 col int,
 view_html_id varchar(32),
 view_css_id varchar(32),
 view_js_id varchar(32),
 view_data_id varchar(32),
 view_data_refresh int default 0 comment '0: no refresh, > 0: seconds of timer',
 memo varchar(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块-视图';



-- drop table tb_tab
-- truncate table tb_tab
-- select * from tb_tab limit 100;
create table tb_tab (
 id varchar(32) primary key,
 system_id varchar(32),
 ename varchar(64),
 show_name varchar(64),
 memo varchar(1024),
 created_time datetime,
 `version` int default 0,
 unique key(system_id, ename, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-虚拟表';

-- drop table tb_column
-- truncate table tb_column
-- select * from tb_column limit 100;
create table tb_column (
 id varchar(32) primary key,
 system_id varchar(32),
 tab_id varchar(32),
 ename varchar(64),
 show_name varchar(64),
 `type` varchar(32) comment '字段类型: int, text, date, time, datetime',
 `regexp_id` varchar(32) comment '自定义格式ID',
 memo varchar(1024),
 created_time datetime,
 `version` int default 0,
 unique key(tab_id, ename, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-虚拟字段表';

-- drop table tb_data
-- truncate table tb_data
-- select * from tb_data limit 100;
create table tb_data (
 id varchar(32) primary key,
 tab_id varchar(32),
 column_id varchar(32),
 value varchar(4096),
 created_time datetime,
 `version` int default 0,
 unique key(tab_id, column_id, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-虚拟字段的数据表';
