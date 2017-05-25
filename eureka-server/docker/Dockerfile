FROM java:8
MAINTAINER "wenterse.lee@gamil.com"
ENV APPROOT=/opt/springcloud\
    APP=eureka-server
COPY ["../../target/eureka-server-0.0.1-SNAPSHOT.jar","${APPROOT}/${APP}/"]

ENTRYPOINT ["java","-jar","/opt/springcloud/eureka-server/eureka-server-0.0.1-SNAPSHOT.jar"]

EXPOSE 8761





