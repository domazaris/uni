create table Client
(
    username varchar(50) not null,
    passwd varchar(50) not null,
    surname varchar(20) not null,
    fname varchar(20) not null,
    email varchar(100) not null,
    phone varchar(20) not null,
    lesson_type varchar(50) not null
)

create table LessonType
(
    name varchar(50) not null,
    cost int not null,
    hours int not null
)

create table Administrator
(
    username varchar(50) not null,
    passwd varchar(50) not null,
    email varchar(100) not null,
    surname varchar(20) not null,
    fname varchar(20) not null
)

create table Document
(
    id int not null,
    date varchar(50) not null,
    link varchar(200),
    client_username varchar(50) not null
)

create table Appointment
(
    id int not null,
    notes varchar(500),
    client_username varchar(50) not null,
    lesson_type varchar(50) not null,
    car int not null
)

create table TimeSlot
(
    id int not null,
    date varchar(50) not null,
    appointment int not null
)


create table Car
(
    license int not null,
    make varchar(50)
)


create table Instructor
(
    username varchar(50) not null,
    passwd varchar(50) not null,
    phone varchar(20) not null,
    email varchar(100) not null,
    fname varchar(20) not null,
    surname varchar(20) not null,
    appointment int not null
)
