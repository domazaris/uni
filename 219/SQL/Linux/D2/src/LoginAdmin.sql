declare @user varchar(20) = 'kbrock'
declare @pass varchar(20) = '123mainguy321'

PRINT 'Logging in user ' + @user

-- Run the select
select * from Administrator where username = @user and passwd = @pass

-- Code for logged in
if @@ROWCOUNT > 0
    PRINT 'Logged in'
else
    PRINT 'Invalid username or password'
