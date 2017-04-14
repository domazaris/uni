declare @user varchar(20) = 'dazaris'
declare @pass varchar(20) = 'passwerd'

-- Run the select
PRINT 'Logging in user ' + @user
select * from Client where username = @user and passwd = @pass

-- Code for logged in
if @@ROWCOUNT > 0
    PRINT 'Logged in'
else
    PRINT 'Invalid username or password'


-- Negative test
PRINT 'Logging in user ' + @user + ' with incorrect password'

set @user = 'dazaris'
set @pass = 'sdgdfgs'
select * from Client where username = @user and passwd = @pass

-- Code for logged in
if @@ROWCOUNT > 0
    PRINT 'Logged in'
else
    PRINT 'Invalid username or password'
