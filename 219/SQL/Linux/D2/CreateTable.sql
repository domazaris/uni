create table Car
(
    license varchar(6) not null,
    make varchar(20),
    primary key(license)
)


create table TimeSlot
(
    id int not null,
    hour int not null,
    date int not null,
    month int not null,
    year int not null,
    primary key(id)
)

create table Administrator
(
    username varchar(10) not null,
    passwd varchar(20) not null,
    email varchar(15) not null,
    fname varchar(20) not null,
    surname varchar(20) not null,
    primary key(username)
)

create table LessonType
(
    name varchar(15) not null,
    cost int not null,
    hours int not null,
    primary key(name)
)

create table Instructor
(
    username varchar(10) not null,
    passwd varchar(20) not null,
    phone varchar(10) not null,
    email varchar(20) not null,
    fname varchar(10) not null,
    surname varchar(10) not null,
    primary key(username)
)

create table Client
(
    username varchar(10) not null,
    passwd varchar(20) not null,
    surname varchar(10) not null,
    fname varchar(10) not null,
    email varchar(20) not null,
    phone varchar(10) not null,
    lesson_type varchar(15) not null,
    primary key(username),
    foreign key(lesson_type) references LessonType
)

create table Document
(
    id int not null,
    date int not null,
    month int not null,
    year int not null,
    client_username varchar(10) not null,
    link varchar(50),
    primary key(id),
    foreign key(client_username) references Client
)

create table Appointment
(
    id int not null,
    client_username varchar(10) not null,
    lesson_type varchar(15) not null,
    car varchar(6) not null,
    instructor varchar(10) not null,
    time_slot int not null,
    notes varchar(50),
    primary key(id),
    foreign key(client_username) references Client,
    foreign key(lesson_type) references LessonType,
    foreign key(car) references Car,
    foreign key(instructor) references Instructor,
    foreign key(time_slot) references TimeSlot
)
