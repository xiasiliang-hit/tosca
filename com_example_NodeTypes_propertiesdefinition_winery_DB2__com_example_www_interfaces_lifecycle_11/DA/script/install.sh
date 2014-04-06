#!/bin/bash

pwd='123456'

db2url='http://192.168.209.217:8080/db2/DB2_Svr_10.5.0.1_Linux_x86-64.tar.gz'

logfile='log.txt'


echo $pwd | sudo -S yum -y install wget &>> $logfile 
echo $pwd | sudo -S yum -y install libpam.so.0 &>> $logfile
echo $pwd | sudo -S yum -y install libaio &>> $logfile
echo $pwd | sudo -S yum -y install libnuma.so.1 &>> $logfile
echo $pwd | sudo -S yum -y install numactl &>> $logfile


wget -N $db2url

tar -zxf DB2_Svr_10.5.0.1_Linux_x86-64.tar.gz

echo $pwd | sudo -S ./server/db2_install << EOF_TEXT
yes
SERVER
no
EOF_TEXT


