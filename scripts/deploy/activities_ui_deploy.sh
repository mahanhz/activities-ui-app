#!/bin/sh

echo "Passed in arguments are: $1, $2, $3"
REPO_ID=$1
VERSION=$2
ENV_CONF=$3

HOME_DIR=/home/pi

echo "This script assumes that the appadm user exists"
APP_USER=appadm

APP_NAME=activities-ui
APP_DIR=$APP_NAME

LOG_DIR=log

NEXUS_URL=http://192.168.1.31:8082/nexus/service/local/artifact/maven/content
GROUP_ID=com.amhzing.activities.ui
ARTIFACT_ID=$APP_NAME
ARTIFACT_URL="$NEXUS_URL?r=$REPO_ID&g=$GROUP_ID&a=$ARTIFACT_ID&v=$VERSION"
ARTIFACT=$APP_NAME.jar
CONF=$APP_NAME.conf

echo "Set up application directory (assuming /opt folder already exists)"
cd /opt
# Create the directory if it doesn't exist
sudo mkdir -p $APP_DIR
# Change owner and group
sudo chown -R $APP_USER:$APP_USER $APP_DIR
# Set the permissions
sudo chmod 755 $APP_DIR

echo "Set up Log directory (assuming /var/opt folder already exists)"
cd /var/opt
sudo mkdir -p $LOG_DIR/$APP_DIR
sudo chown -R $APP_USER:$APP_USER $LOG_DIR
sudo chown -R $APP_USER:$APP_USER $LOG_DIR/$APP_DIR
sudo chmod -R 755 $LOG_DIR
sudo chmod -R 755 $LOG_DIR/$APP_DIR

echo "Get the jar file from Nexus"
cd /opt/$APP_DIR
sudo rm -r /opt/$APP_DIR/*
echo "$ARTIFACT_URL"
wget -qO $ARTIFACT $ARTIFACT_URL

if [ -s $ARTIFACT ]
then
    echo "Got $ARTIFACT for version $VERSION"
else
    echo "Could not get $ARTIFACT for version $VERSION"
    exit 1
fi

sudo chown -R $APP_USER:$APP_USER $ARTIFACT
sudo chmod 500 $ARTIFACT

echo "Move the conf folder (this assumes that the conf file was placed in the user's home directory)"
cd $HOME_DIR
sudo mv $ENV_CONF /opt/$APP_DIR
cd /opt/$APP_DIR
sudo mv $ENV_CONF $CONF
sudo chown -R $APP_USER:$APP_USER $CONF
sudo chmod 500 $CONF

echo "Create a symlink"
cd /etc/init.d
sudo rm $APP_NAME
sudo ln -s /opt/$APP_DIR/$ARTIFACT $APP_NAME

echo "Set up the application to start automatically on boot"
sudo update-rc.d $APP_NAME remove
sudo update-rc.d $APP_NAME defaults 50

echo "Kill the PID"
ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}' | xargs kill

echo "Restart the app"
sudo service $APP_NAME restart
