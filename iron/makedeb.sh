#!/usr/bin/env bash

VERS=1.0.0
TARGET_DIR=../target
cd $(dirname $0);
CURRDIR=$(pwd)

workdir=$(mktemp -d /tmp/mkdebian_XXXXXXXX)
mkdir -p ${workdir}/DEBIAN
cp -r ./debian/* ${workdir}/DEBIAN
#./configure "$@"
./configure --prefix=/
make install DESTDIR=${workdir}
test -d $TARGET_DIR || mkdir -p $TARGET_DIR
dpkg-deb --build ${workdir} $TARGET_DIR/iron-$VERS.deb

