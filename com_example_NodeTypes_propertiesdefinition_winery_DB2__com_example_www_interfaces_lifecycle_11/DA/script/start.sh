#!/bin/bash

db2path='/opt/ibm/db2/V10.5'
inspath=${db2path}'/instance'

db2adminGP='db2adm'
db2insGP='db2ins'

db2adminUser='db2adm0'
db2insUser='db2ins0'

port='50000'


logfile='log.txt'

pwd='123456'

# db2set DB2_EXTENDED_OPTIMIZATION=ON
# db2set DB2_DISABLE_FLUSH_LOG=ON
# db2set AUTOSTART=YES
# db2set DB2_STRIPED_CONTAINERS=ON
# db2set DB2_HASH_JOIN=Y
# db2set DB2COMM=tcpip
# db2set DB2_PARALLEL_IO=*
# db2set DB2CODEPAGE=819
# db2 update dbm cfg using SVCENAME db2ins0
# db2 update dbm cfg using INDEXREC ACCESS


#add user
sudo groupadd $db2adminGP 2> $logfile
sudo groupadd $db2insGP 2> $logfile

sudo useradd $db2adminUser 2> $logfile
sudo useradd $db2insUser 2> $logfile

sudo passwd $db2adminUser << EOF
$pwd
$pwd
EOF

sudo passwd $db2insUser << EOF
$pwd
$pwd
EOF

#crt instance
echo $pwd | sudo -S $inspath/dascrt $db2adminUser &>> $logfile
echo $pwd | sudo -S $inspath/db2icrt -p $port -u $db2adminUser $db2insUser &>> $logfile


echo $pwd | sudo -S chmod 777 /etc/sysconfig/iptables 

echo $pwd | sudo -S sed "12 a-A RH-Firewall-1-INPUT -m state --state NEW -m tcp -p tcp --dport 50000 -j ACCEPT" -i /etc/sysconfig/iptables 

echo $pwd | sudo -S service iptables stop
echo $pwd | sudo -S service iptables start

echo $pwd | sudo -u db2ins0 -H sh -c "~/sqllib/db2profile" &>> $logfile

echo $pwd | sudo -u db2ins0 -H sh -c "~/sqllib/adm/db2set DB2COMM=npipe,tcpip" &>> $logfile

echo $pwd | sudo -u db2ins0 -H sh -c "~/sqllib/adm/db2start" &>> $logfile
