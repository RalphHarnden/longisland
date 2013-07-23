#!/bin/sh

sudo cp /usr/local/magnet/bin/auditService.sh /etc/init.d/auditService

sudo update-rc.d auditService defaults 98

