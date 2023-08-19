FROM openjdk:8-jre
USER root
WORKDIR /opt/app
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
ARG JAR_FILE
COPY ${JAR_FILE} /opt/app/app.jar
RUN chmod 755 app.jar
ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-Ddruid.mysql.usePingMethod=false", "-jar", "/opt/app/app.jar"]
CMD ["--spring.profiles.active=prod"]