#! /bin/bash -x

#-DmaxPostSize=1048576
server_port=8081

cd $(dirname $0); currdir=/tmp

java -Dserver.port=${server_port}\
	-DmaxPostSize=10485760\
	 -jar certsign.jar
