#!/bin/sh

MAGNET_ROOT=audit-root
mkdir -p $MAGNET_ROOT

CONFIG_DIR='./audit.config.dir'
mkdir -p $MAGNET_ROOT/data.dir/logs

SERVER_DIRS="-Dmagnet_root=$MAGNET_ROOT -Dconfig.dir=$CONFIG_DIR"

# Set the default heap sizes to a reasonable default that will minimize GCs
# This will start with a 1GB heap increasing to 2GB if needed.
JAVA_HEAP_OPTS='-Xms256m -Xmx256m'

JAVA_OPTS="$JAVA_HEAP_OPTS $SERVER_DIRS"

echo "Starting Magnet Enterprise Server..."

java $JAVA_OPTS -jar lib/magnet-platform-providers-audit-s3-uploader.jar
