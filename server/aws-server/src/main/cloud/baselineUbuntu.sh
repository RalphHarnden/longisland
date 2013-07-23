#!/bin/sh

# make sure we have the lastest OS upgrades
sudo apt-get update
sudo apt-get upgrade

# update the time zone.
sudo dpkg-reconfigure tzdata

# install zip and unzip
sudo apt-get install zip

# install Java 7 from a PPA
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer
sudo apt-get install oracle-java7-set-default

# setup the magnet user
sudo adduser --disabled-password --home /usr/local/magnet magnet

# if you need to connect directly as the magnet user...
#sudo -u magnet mkdir /usr/local/magnet/.ssh
#set permission to private
#sudo -u magnet chmod 700 /usr/local/magnet/.ssh
#add users keys
#sudo -u magnet vi /usr/local/magnet/.ssh/authorized_keys
#set permission to private
#sudo -u magnet chmod 600 /usr/local/magnet/.ssh/authorized_keys
