-- This code generates time slots for 1 month (4 weeks), 7am-8pm, Monday to Saturday

-- init ID
declare @id INT = 0

-- Iterate 4 weeks ( ~1 month )
declare @week INT = 1
while @week <= 4
begin
    -- For each week, go through the first six days (mon-sat)
    declare @day INT = 1
    while @day <= 6 -- 5 work days + saturday
    begin
        -- For each day, go through each hour from 7am-7pm
        declare @hour INT = 7 -- start at 7am
        while @hour <= 19 -- last session at 7pm
        begin
            -- Create the timeslot
            insert into TimeSlot values(@id, @hour, @day + (@week * 7), 3, 2017)
            set @id = @id + 1
            set @hour = @hour + 1
        end
        SET @day = @day + 1
    end
    SET @week = @week + 1
end
