#! /bin/bash

## Create the database
sqlcmd -S localhost -U SA -P sqlSQL123 -i src/CreateDB.sql

## Create all of the tables needed
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/CreateTable.sql

## Populate some tables with data
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/Populate.sql

## Create time slots for appointments
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/CreateTimeSlots.sql

## Register a new client
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/RegisterClient.sql

## Print all of the tables
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/PrintAll.sql

## Delete the database
sqlcmd -S localhost -U SA -P sqlSQL123 -i src/DeleteDB.sql

