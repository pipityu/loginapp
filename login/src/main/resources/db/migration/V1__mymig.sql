create table user(
id int not null primary key auto_increment,
email varchar(128) not null,
password varchar(64) not null,
fullName varchar(128) not null,
phone varchar(16)
);

create table role(
id int primary key auto_increment not null,
rolename varchar(16) not null
);

create table user_role(
id int primary key auto_increment not null,
userid int not null,
roleid int not null,
foreign key(userid) references user(id),
foreign key(roleid) references role(id)
);

insert ignore into role (id, rolename) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

/*INSERT SOME DEFAULT DATA*/
insert into user values
(1,'x@x.hu','$2y$12$wIPlJuOzR2Zv42xJqeSlxOeTlNiBeBb73uxox3FHkUM8RnPwvHJtS','Test1 User', '0620333441'),
(2,'y@y.hu','$2y$12$39bOfDAPhXQGKwoVoGqPLOEWdnCj4EYJAmUYQCW2pRET6NsqNznhu','Test2 User', '0630333442'),
(3,'z@z.hu','$2y$12$8GrS5LoffCj6x4N1tUc/c.Rz.tGGHrq6CV25FH7BTq8riw0.qGFUy','Test3 User', '0670333443');

insert into user_role values/* x:user, y:admin ,z:user*/
(1,1,2),
(2,2,1),
(3,2,2),
(4,3,2);
