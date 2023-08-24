#FROM openjdk:8-jre
#USER root
#WORKDIR /opt/app
#RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone
#ARG JAR_FILE
#COPY ${JAR_FILE} /opt/app/app.jar
#RUN chmod 755 app.jar
#ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-Ddruid.mysql.usePingMethod=false", "-jar", "/opt/app/app.jar"]
#CMD ["--spring.profiles.active=prod"]

FROM openjdk:8-jre as builder
USER root
WORKDIR /opt/package
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar  extract

FROM openjdk:8-jre
USER root
WORKDIR /opt/package
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
COPY entrypoint.sh entrypoint.sh
RUN chmod u+x /opt/package/entrypoint.sh
COPY --from=builder /opt/package/dependencies/ ./
COPY --from=builder /opt/package/spring-boot-loader/ ./
COPY --from=builder /opt/package/snapshot-dependencies/ ./
COPY --from=builder /opt/package/application/ ./
ENV ENABLED="true"
ENTRYPOINT ["/opt/package/entrypoint.sh"]
