#!/bin/sh

waitforresponse() 
{
    HOST=$1
    PORT=$2
    while ! nc -z ${HOST} ${PORT};
    do
        echo "waiting for $HOST:$PORT";
        sleep 1;
    done;
    echo Connected in ${HOST}:${PORT}!;
}

waitforresponse $RABBITMQ_HOST $RABBITMQ_PORT
