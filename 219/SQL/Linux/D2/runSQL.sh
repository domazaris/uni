#! /bin/bash
sqlcmd -S localhost -U SA -P sqlSQL123 -i src/CreateDB.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/CreateTable.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/Populate.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/CreateTimeSlots.sql
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/PrintAll.sql
sqlcmd -S localhost -U SA -P sqlSQL123 -i src/DeleteDB.sql

