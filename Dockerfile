FROM amazoncorretto:17

LABEL maintainer="sagartalagatti@gmail.com"

WORKDIR /app

COPY target/springboot-restful-webservices-0.0.1-SNAPSHOT.jar /app/springboot-restful-webservices.jar

ENTRYPOINT ["java","-jar","springboot-restful-webservices.jar"]