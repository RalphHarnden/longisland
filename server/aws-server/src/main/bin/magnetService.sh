#!/bin/sh

# cd to the target directory so that
# configuration and log directories are
# all properly relative.
MAGNET_HOME=/usr/local/magnet
MAGNET_USER=magnet

cd $MAGNET_HOME

case "$1" in
  start)
	sudo -u $MAGNET_USER ./bin/startInstance.sh > /dev/null &
	;;
  stop)
	sudo -u $MAGNET_USER ./bin/stopInstance.sh
	;;
  *)
	echo "Usage: $0 start|stop" >&2
	exit 3
	;;
esac

:

