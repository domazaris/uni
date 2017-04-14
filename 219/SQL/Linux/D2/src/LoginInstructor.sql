declare @user varchar(20) = 'arod'
declare @pass varchar(20) = '1baseball1'

PRINT 'Logging in user ' + @user

-- Run the select
select * from Instructor where username = @user and passwd = @pass

-- Code for logged in
if @@ROWCOUNT > 0
    PRINT 'Logged in'
else
    PRINT 'Invalid username or password'
