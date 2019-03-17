drop table if exists users;

CREATE TABLE users(
  id int not null primary key AUTO_INCREMENT,
  name varchar(64)
);

insert into users (name) values ('John');
insert into users (name) values ('Mike');
insert into users (name) values ('Mary');
