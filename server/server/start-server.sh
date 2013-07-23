#!/bin/sh

echo "Starting Magnet Enterprise Server..."
java -Ddata.dir=src/test/resources/data.dir -Dconfig.dir=src/test/resources/config.dir -jar target/longisland-server-1.jar -setupDefaultUser magnet phTz
