FROM openjdk:8-jre-alpine
VOLUME /tmp
COPY target/*.jar /app.jar
COPY wait_container.sh /wait_container.sh
RUN chmod +x /wait_container.sh

RUN apk update
RUN apk upgrade
RUN apk add bash

ENTRYPOINT ["/wait_container.sh"]
