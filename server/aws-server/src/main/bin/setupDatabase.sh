#!/bin/sh

MYSQL_HOME="/usr/bin"
APP_DB_NAME=longisland
APP_DBUSER_NAME=db_user
APP_DBUSER_PWD=db_password

$MYSQL_HOME/mysqladmin -v -u root -f drop $APP_DB_NAME
$MYSQL_HOME/mysqladmin -v -u root create $APP_DB_NAME
$MYSQL_HOME/mysql -v -u root $APP_DB_NAME -e "GRANT ALL ON $APP_DB_NAME.* TO '$APP_DBUSER_NAME'@'localhost' IDENTIFIED BY '$APP_DBUSER_PWD'"
