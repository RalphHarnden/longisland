#!/bin/sh

echo "Starting Magnet Enterprise Server in debug mode listening on port 8000..."
java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n  -Ddata.dir=src/test/resources/data.dir -Dconfig.dir=src/test/resources/config.dir -jar target/longisland-server-1.jar -setupDefaultUser magnet phTz
