#! /bin/bash

cd $(dirname $0);
currdir=$(pwd)

REMOTE_CA_HOST=127.0.0.1
REMOTE_CA_PORT=8081

caricandum=$1
sname=$2
cname=$3

curl -F "file=@$caricandum" http://$REMOTE_CA_HOST:$REMOTE_CA_PORT/upload/$sname/$cname
