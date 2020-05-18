drop table if exists t_coffee;
drop table if exists t_order;
drop table if exists t_order_coffee;

create table t_coffee (
    id bigint not null auto_increment,
    name varchar(255) not null default '' comment '名称',
#     price decimal(19, 2) default 0.00 comment '价格',
    price bigint default 0 comment '价格',
    created_time datetime,
    updated_time datetime,
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table t_order (
    id bigint not null auto_increment,
    customer varchar(255) not null default '-' comment '顾客姓名',
    state integer not null comment '订单状态',
#     price decimal(19, 2) default 0.00 comment '价格',
    price bigint default 0 comment '价格',
    created_time datetime comment '创建时间',
    updated_time datetime comment '更新时间',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table t_order_coffee (
    coffee_order_id bigint not null comment '订单id',
    items_id bigint not null comment '咖啡id'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

insert into t_coffee (name, price, created_time, updated_time) values ('espresso', 2000, now(), now());
insert into t_coffee (name, price, created_time, updated_time) values ('latte', 2500, now(), now());
insert into t_coffee (name, price, created_time, updated_time) values ('capuccino', 2500, now(), now());
insert into t_coffee (name, price, created_time, updated_time) values ('mocha', 3000, now(), now());
insert into t_coffee (name, price, created_time, updated_time) values ('macchiato', 3000, now(), now());