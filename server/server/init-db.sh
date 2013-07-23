#!/bin/sh

#
# Initialize the DB
#
Echo "Creating DB Schemas..."
mvn exec:exec -Dexec.executable="java" -Dexec.args="-cp %classpath -Dconfig.dir=src/test/resources/config.dir com.magnet.OpusMain -jdbcCreateSchema MySqlJdbcPersister-1"
