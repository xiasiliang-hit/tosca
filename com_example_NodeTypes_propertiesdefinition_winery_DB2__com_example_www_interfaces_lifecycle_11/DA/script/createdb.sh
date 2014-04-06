#!/bin/bash

#first argument is the database name
pwd='123456'
db2user='db2ins0'
dbname=$1

cd /home/$db2user
echo $pwd | sudo -u db2ins0 -H sh -c "~/sqllib/bin/db2 create database "$dbname
