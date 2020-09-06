create table if not exists category
(
	id serial not null
		constraint category_pkey
			primary key,
	name varchar(50),
	description varchar(250)
);



create table if not exists db_user
(
	id serial not null
		constraint db_user_pkey
			primary key,
	email varchar(50) not null
		constraint db_user_email_key
			unique,
	username varchar(50) not null
		constraint db_user_username_key
			unique,
	password varchar(100),
	role varchar(30)
);

create table if not exists item
(
	id serial not null
		constraint item_pkey
			primary key,
	item_code varchar(30) not null
		constraint item_item_code_key
			unique,
	name varchar(50) not null,
	description varchar(250) not null,
	price float4 not null
);

create table if not exists item_category
(
	item_id bigint not null
		constraint fk_item_category
			references item,
	category_id bigint not null
		constraint fk_category_item
			references category,
	constraint item_category_pkey
		primary key (item_id, category_id)
);

create table if not exists supplier
(
	id serial not null
		constraint supplier_pkey
			primary key,
	name varchar(50) not null
		constraint supplier_name_key
			unique
);

create table if not exists warehouse
(
	id serial not null
		constraint warehouse_pkey
			primary key,
	address varchar(100) not null
);

create table if not exists invoice
(
	id serial not null
		constraint invoice_pkey
			primary key,
	arrived_at timestamp(6) not null,
	warehouse_id bigint not null
		constraint fk_invoice_warehouse
			references warehouse,
	supplier_id bigint not null
		constraint fk_invoice_supplier
			references supplier,
	is_confirmed bool default false
);

create table if not exists item_invoice
(
	item_id bigint not null
		constraint fk_item_invoice
			references item,
	invoice_id bigint not null
		constraint fk_invoice_item
			references invoice,
	quantity int4 not null,
	constraint item_invoice_pkey
		primary key (item_id, invoice_id)
);

create table if not exists item_warehouse
(
	item_id bigint not null
		constraint fk_item_warehouse
			references item,
	warehouse_id bigint not null
		constraint fk_warehouse_item
			references warehouse,
	quantity int4 not null,
	constraint item_warehouse_pkey
		primary key (item_id, warehouse_id)
);

create table if not exists shop
(
	id serial not null
		constraint shop_pkey
			primary key,
	name varchar(50) not null
		constraint shop_name_key
			unique,
	warehouse_id bigint not null
		constraint fk_shop_warehouse
			references warehouse
);

create table if not exists db_order
(
	id serial not null
		constraint db_order_pkey
			primary key,
	is_confirmed bool default false,
	ordered_at timestamp(6),
	customer_id bigint not null
		constraint fk_order_customer
			references db_user,
	shop_id bigint not null
		constraint fk_order_shop
			references shop
);

create table if not exists item_order
(
	item_id bigint not null
		constraint fk_item_order
			references item,
	order_id bigint not null
		constraint fk_order_item
			references db_order,
	quantity int4 not null,
	constraint item_order_pkey
		primary key (item_id, order_id)
);


create table if not exists writeoff
(
	id serial not null
		constraint writeoff_pkey
			primary key,
	time timestamp(6) not null,
	warehouse_id bigint not null
		constraint fk_writeoff_warehouse
			references warehouse,
	is_confirmed bool default false
);

create table if not exists item_writeoff
(
	item_id bigint not null
		constraint fk_item_writeoff
			references item,
	writeoff_id bigint not null
		constraint fk_writeoff_item
			references writeoff,
	quantity int4 not null,
	constraint item_writeoff_pkey
		primary key (item_id, writeoff_id)
);

