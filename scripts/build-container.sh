#!/bin/bash

docker stop school-registration-db && docker rm school-registration-db
docker run \
  --name school-registration-db \
  --platform linux/x86_64 \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=mysqlpass \
  -e MYSQL_DATABASE=school-registration-db \
  -d mysql:8.0