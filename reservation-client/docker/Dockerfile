FROM java:8
MAINTAINER "wenterse.lee@gamil.com"

COPY ["../../target/reservation-client-0.0.1-SNAPSHOT.jar","/opt/springcloud/business/"]

ENTRYPOINT ["java","-jar","/opt/springcloud/business/reservation-client-0.0.1-SNAPSHOT.jar"]

EXPOSE 8081

