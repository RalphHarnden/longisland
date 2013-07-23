#!/bin/sh
# Run this script from your project root.

#INSTANCE_NAME=_ENTER_YOUR_RUNNING_INSTANCE_PUBLIC_DNS
#IDENTITY=ENTER_YOUR_RUNNING_INSTANCE_PUBLIC_DNS.pem

SCP_OPTS="-oStrictHostKeyChecking=no -i $IDENTITY"
SSH_OPTS="-oStrictHostKeyChecking=no -i $IDENTITY ubuntu@$INSTANCE_NAME"

# Figure out how to get the version number from the pom
ARTIFACT_NAME='longisland-server-aws-1-deploy.zip'
TARGET_USER=magnet
TARGET_DIR=/usr/local/magnet

# set up trust
ssh  $SSH_OPTS uname -a

# copy the bits to the server
scp  $SCP_OPTS target/$ARTIFACT_NAME ubuntu@$INSTANCE_NAME:$ARTIFACT_NAME

# unzip the bits and make the scripts executable and chown to the target user
ssh $SSH_OPTS sudo -u $TARGET_USER unzip -o -d $TARGET_DIR $ARTIFACT_NAME
ssh $SSH_OPTS sudo -u $TARGET_USER chmod +x $TARGET_DIR/bin/*

# install the start script as a service instance.
ssh $SSH_OPTS $TARGET_DIR/bin/setupDatabase.sh
ssh $SSH_OPTS sudo -u $TARGET_USER -i ./bin/setupSchema.sh

 # install the start script as a service instance.
ssh $SSH_OPTS $TARGET_DIR/bin/installService.sh

# install the start auditing as a service.
ssh $SSH_OPTS $TARGET_DIR/bin/installAuditService.sh
