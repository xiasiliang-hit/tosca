#!/bin/bash

pwd='123456'
db2user='db2ins0'


cd /home/$db2user
echo $pwd | sudo -u db2ins0 -H sh -c "/home/db2ins0/sqllib/adm/db2stop"
