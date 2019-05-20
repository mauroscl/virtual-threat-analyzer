FROM openjdk:8-jre-alpine
VOLUME /tmp
COPY target/*.jar /app.jar
COPY wait_container.sh /wait_container.sh
RUN chmod +x /wait_container.sh

ENTRYPOINT ["/wait_container.sh"]
CMD ["java","-jar","/app.jar"]