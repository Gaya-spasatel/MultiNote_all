--     mysql -u root -p multinote < file_name

-- Создаём БД в правильной кодировке
CREATE DATABASE multinote CHARACTER SET utf8 COLLATE utf8_unicode_ci;

use multinote;

-- Создаем таблицу users
create table users (
id INT NOT NULL AUTO_INCREMENT,
login VARCHAR(50) NOT NULL,
password CHAR(41) NOT NULL,
email VARCHAR(50) NOT NULL,
PRIMARY KEY ( id ));

-- Создаем таблицу access
create table access (
id INT NOT NULL AUTO_INCREMENT,
id_note INT NOT NULL,
access_login VARCHAR(50) NOT NULL,
PRIMARY KEY ( id ));

-- Создаем таблицу notes
create table notes (
id INT NOT NULL AUTO_INCREMENT,
owner VARCHAR(50) NOT NULL,
text VARCHAR(65000) NOT NULL,
last_modified TIMESTAMP NOT NULL,
login_modified VARCHAR(50) NOT NULL,
is_private BOOL NOT NULL DEFAULT 1,
is_modified BOOL NOT NULL,
PRIMARY KEY ( id ));

-- users
-- +----------+-------------+------+-----+---------+----------------+
-- | Field    | Type        | Null | Key | Default | Extra          |
-- +----------+-------------+------+-----+---------+----------------+
-- | id       | int(11)     | NO   | PRI | NULL    | auto_increment |
-- | login    | varchar(50) | NO   |     | NULL    |                |
-- | password | char(41)    | NO   |     | NULL    |                |
-- | email    | varchar(50) | NO   |     | NULL    |                |
-- +----------+-------------+------+-----+---------+----------------+

insert into users (login,password,email) values ('user','password','email@example.com');
insert into users (login,password,email) values ('user2','password2','email2@example.com');
insert into users (login,password,email) values ('user3','password3','email3@example.com');

-- notes
-- +----------------+----------------+------+-----+-------------------+-----------------------------+
-- | Field          | Type           | Null | Key | Default           | Extra                       |
-- +----------------+----------------+------+-----+-------------------+-----------------------------+
-- | id             | int(11)        | NO   | PRI | NULL              | auto_increment              |
-- | owner          | varchar(50)    | NO   |     | NULL              |                             |
-- | text           | varchar(65000) | NO   |     | NULL              |                             |
-- | last_modified  | timestamp      | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
-- | login_modified | varchar(50)    | NO   |     | NULL              |                             |
-- | is_private     | tinyint(1)     | NO   |     | 1                 |                             |
-- | is_modified    | tinyint(1)     | NO   |     | NULL              |                             |
-- +----------------+----------------+------+-----+-------------------+-----------------------------+

insert into notes (owner,text,last_modified,login_modified,is_private,is_modified) values ('user','lala',NULL,'user',0,0);
insert into notes (owner,text,last_modified,login_modified,is_private,is_modified) values ('user','ЛаЛа-Ла-лала-лааааа',NULL,'user',0,1);
insert into notes (owner,text,last_modified,login_modified,is_private,is_modified) values ('user2','lolo',NULL,'user2',0,0);
insert into notes (owner,text,last_modified,login_modified,is_private,is_modified) values ('user2','ЛоЛо-ллаа-лооо2',NULL,'user',0,0);
insert into notes (owner,text,last_modified,login_modified,is_private,is_modified) values ('user3','А вот это просто тест',NULL,'user2',0,1);

-- access;
-- +--------------+-------------+------+-----+---------+----------------+
-- | Field        | Type        | Null | Key | Default | Extra          |
-- +--------------+-------------+------+-----+---------+----------------+
-- | id           | int(11)     | NO   | PRI | NULL    | auto_increment |
-- | id_note      | int(11)     | NO   |     | NULL    |                |
-- | access_login | varchar(50) | NO   |     | NULL    |                |
-- +--------------+-------------+------+-----+---------+----------------+

insert into access (id_note,access_login) values (1,'user2');
insert into access (id_note,access_login) values (1,'user3');
insert into access (id_note,access_login) values (2,'user2');
insert into access (id_note,access_login) values (2,'user3');
insert into access (id_note,access_login) values (3,'user');
insert into access (id_note,access_login) values (3,'user3');
insert into access (id_note,access_login) values (4,'user');
insert into access (id_note,access_login) values (4,'user3');
insert into access (id_note,access_login) values (5,'user2');
insert into access (id_note,access_login) values (5,'user');

