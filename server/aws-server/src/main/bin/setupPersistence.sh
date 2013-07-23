#!/bin/sh

MYSQL_HOME="/usr/bin"
APP_DB_NAME=longisland
APP_DBUSER_NAME=db_user
APP_DBUSER_PWD=db_password

APPLICATION_JAR='longisland-server-${project.version}.jar'

# temp direcotry for the ddl script extracted from the jar
mkdir -p mysql

cd mysql

jar xvf ../lib/$APPLICATION_JAR "config.dir/mysql"

if [ -f "./config.dir/mysql/ddl.sql"]
then
   $MYSQL_HOME/mysql -v -u $APP_DBUSER_NAME -p$APP_DBUSER_PWD < "./config.dir/mysql/ddl.sql"
fi