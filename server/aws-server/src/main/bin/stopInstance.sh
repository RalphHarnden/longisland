#!/bin/sh

echo 'Stopping longisland-server'

TARGET="\-jar lib/longisland\-server"

pid=`ps -aef | grep "$TARGET" | grep -v grep | awk '{print $2}' | tr "\n" " "`

if [ -n "$pid" ]; then
  echo 'Sending SIGINT to $pid'
  kill -INT $pid
else
  echo "Process not running"
  exit
fi

sleep 1

count=1
while [ $count -lt 6 ]
do
  pid=`ps -aef | grep "$TARGET" | grep -v grep | awk '{print $2}' | tr "\n" " "`
  if [ -n "$pid" ]; then
    echo 'Waiting for $pid to exit'
    count=$(($count+1))
    sleep 5
  else
    echo 'Process terminated'
    exit
  fi
done

if [ $pid ]; then
  echo 'Sending SIGKILL to $pid'
  kill -KILL $pid
fi
