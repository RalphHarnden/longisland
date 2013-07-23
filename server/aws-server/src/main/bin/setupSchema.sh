#!/bin/sh

APPLICATION_JAR='longisland-server-${project.version}.jar'
MAGNET_ROOT=.

# must match the configuration in the associated config.dir/logging.properties
mkdir -p $MAGNET_ROOT/data.dir/logs

# this could be templatized.
JDBC_CONFIG=MySqlJdbcPersister-1

SERVER_DIRS="-Dmagnet_root=$MAGNET_ROOT"

JAVA_OPTS="$SERVER_DIRS -DJettyConfig-1=false"

echo "Preparing database..."
echo "Dropping existing schema..."
java $JAVA_OPTS -jar lib/$APPLICATION_JAR -jdbcDropSchema $JDBC_CONFIG

echo "Creating new schema..."
java $JAVA_OPTS -jar lib/$APPLICATION_JAR -jdbcCreateSchema $JDBC_CONFIG
