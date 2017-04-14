-- Iterate 4 weeks ( ~1 month )
declare @id INT = 0
declare @week INT = 1
while @week <= 4
begin
    declare @day INT = 1
    while @day <= 6 -- 5 work days + saturday
    begin
        declare @hour INT = 7 -- start at 7am
        while @hour <= 19 -- last session at 7pm
        begin
            insert into TimeSlot values(@id, @hour, @day + (@week * 7), 3, 2017)
            set @id = @id + 1
            set @hour = @hour + 1
        end

        SET @day = @day + 1
    end
    SET @week = @week + 1
end
