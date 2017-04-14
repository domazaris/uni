#! /bin/bash

## Create the database
sqlcmd -S localhost -U SA -P sqlSQL123 -i src/CreateDB.sql
echo ""

## Create all of the tables needed
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/CreateTable.sql
echo ""

## Populate some tables with data
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/Populate.sql
echo ""

## Create time slots for appointments
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/CreateTimeSlots.sql
echo ""

## Register a new client & log in
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/RegisterClient.sql
echo ""
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/LoginClient.sql
echo ""

## Login as admin and instructor
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/LoginAdmin.sql
echo ""
sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/LoginInstructor.sql
echo ""

## Print all of the tables
# sqlcmd -S localhost -d a2d2 -U SA -P sqlSQL123 -i src/PrintAll.sql
echo ""

## Delete the database
sqlcmd -S localhost -U SA -P sqlSQL123 -i src/DeleteDB.sql

