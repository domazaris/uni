#! /bin/bash
sqlcmd -w 80 -S localhost -U SA -P sqlSQL123 -i CreateDB.sql
sqlcmd -w 80 -S localhost -d a2d2  -U SA -P sqlSQL123 -i CreateTable.sql
sqlcmd -w 80 -S localhost -d a2d2  -U SA -P sqlSQL123 -i Populate.sql
sqlcmd -w 80 -S localhost -d a2d2  -U SA -P sqlSQL123 -i PrintAll.sql
sqlcmd -w 80 -S localhost -U SA -P sqlSQL123 -i DeleteDB.sql

