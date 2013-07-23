#!/bin/sh

APPLICATION_JAR='longisland-server-${project.version}.jar'
MAGNET_ROOT=.

# must match the configuration in the associated config.dir/logging.properties
mkdir -p $MAGNET_ROOT/data.dir/logs

# The default logging.properties uses the simpleformatter to write the logs.
# This option will only work from the command line
LOG_FORMAT='-Djava.util.logging.SimpleFormatter.format=[%1$tc]%4$-8s:%5$s%n'

SERVER_DIRS="-Dmagnet_root=$MAGNET_ROOT"

# Uncomment this to enable remote JMX access
#JMX_REMOTE='-Dcom.sun.management.jmxremote'
#JMX_PORT='-Dcom.sun.management.jmxremote.port=<selected port number>'

# Uncomment this to enable remote debugging
#REMOTE_DEBUG='-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n'

# Set the default heap sizes to a reasonable default that will minimize GCs
# This will start with a 1GB heap increasing to 2GB if needed.
#JAVA_HEAP_OPTS='-Xms1024m -Xmx2048m'

JAVA_OPTS="$JAVA_HEAP_OPTS $LOG_FORMAT $REMOTE_DEBUG $JMX_REMOTE $JMX_PORT $SERVER_DIRS"

echo "Starting Magnet Enterprise Server..."
echo "java $JAVA_OPTS -jar lib/$APPLICATION_JAR"

java $JAVA_OPTS -jar lib/$APPLICATION_JAR -setupDefaultUser magnet phTz
