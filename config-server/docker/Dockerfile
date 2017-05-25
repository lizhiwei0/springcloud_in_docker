FROM java:8
MAINTAINER "wenterse.lee@gmail.com"
ENV APPROOT=/opt/springcloud \
    APP=configserver
WORKDIR ${APPROOT}/${APP}
#COPY the built config-server jar file into our image
COPY ["./../target/config-server-0.0.1-SNAPSHOT.jar","${APPROOT}/${APP}"]
#user bind volum instead of internal folder
#ADD  ["./config-repo","/var/config-repo"]

EXPOSE 8888

ENTRYPOINT ["java","-jar","/opt/springcloud/configserver/config-server.jar"]

