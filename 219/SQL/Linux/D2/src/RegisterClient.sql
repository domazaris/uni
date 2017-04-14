-- Basic registration of client

declare @uname varchar(20) = 'dazaris'
declare @pwd   varchar(20) = 'passwerd'
declare @fname varchar(20) = 'azaris'
declare @sname varchar(20) = 'dominic'
declare @email varchar(20) = 'dazaris@dom.com'
declare @phone varchar(20) = '555123321'
declare @type  varchar(20) = 'experienced'

insert into Client values(@uname, @pwd, @fname, @sname, @email, @phone, @type)
