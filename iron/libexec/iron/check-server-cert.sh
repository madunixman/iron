#! /bin/bash -x

openssl verify -CAfile ca.crt  server.crt
