create table accounts (
	id long not null,
	name varchar(100),
	usdt double,
	btc double,
	eth double
);

create table btcusdt (
	exchange varchar(100),
	buy double,
	sell double
);

create table ethusdt (
	exchange varchar(100),
	buy double,
	sell double
);