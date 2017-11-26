#! /bin/bash

cd $(dirname $0);
currdir=$(pwd)

spring jar ${currdir}/certsign.jar ${currdir}/certsign.groovy
