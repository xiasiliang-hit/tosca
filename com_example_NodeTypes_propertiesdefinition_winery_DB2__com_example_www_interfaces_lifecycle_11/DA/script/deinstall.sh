#!/bin/bash

pwd='123456'
db2user='db2ins0'
db2path='/opt/ibm/db2/V10.5'

cd /home/$db2user
echo $pwd | sudo -u db2ins0 -H sh -c "/home/db2ins0/sqllib/adm/db2stop force"

echo $pwd | sudo -S ./server/db2_deinstall -a << EOF_TEXT
$db2path
EOF_TEXT

