#!/bin/sh
# Run this script from your project root.

#INSTANCE_NAME=_ENTER_YOUR_RUNNING_INSTANCE_PUBLIC_DNS
#IDENTITY=ENTER_YOUR_RUNNING_INSTANCE_PUBLIC_DNS.pem

SCP_OPTS=-oStrictHostKeyChecking=no -i $IDENTITY
SSH_OPTS=-oStrictHostKeyChecking=no -i $IDENTITY ubuntu@$INSTANCE_NAME

ssh -i $IDENTITY ubuntu@$INSTANCE_NAME uname -a

# configure java, zip, and other useful utilities.
scp $SCP_OPTS src/main/cloud/baselineUbuntu.sh ubuntu@$INSTANCE_NAME:baselineUbuntu.sh
ssh $SSH_OPTS chmod +x baselineUbuntu.sh
ssh $SSH_OPTS ./baselineUbuntu.sh

# for single instance deployments with a local mysql database.
scp $SCP_OPTS src/main/cloud/mysqlUbuntu.sh ubuntu@$INSTANCE_NAME:mysqlUbuntu.sh
ssh $SSH_OPTS chmod +x mysqlUbuntu.sh
ssh $SSH_OPTS ./mysqlUbuntu.sh