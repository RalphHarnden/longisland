#!/bin/sh

sudo cp /usr/local/magnet/bin/magnetService.sh /etc/init.d/magnetService

sudo update-rc.d magnetService defaults 99

