#! /bin/bash
sqlcmd -S localhost -U SA -P sqlSQL123 -i CreateDB.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i CreateTable.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i Populate.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i CreateTimeSlots.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i PrintAll.sql
sqlcmd -S localhost -U SA -P sqlSQL123 -i DeleteDB.sql

