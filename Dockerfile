FROM openjdk:23-jdk
VOLUME /tmp
EXPOSE 8080
ADD target/UploadImageAsync-0.0.1.jar UploadImageAsync-0.0.1.jar
ENTRYPOINT ["java","-jar","/UploadImageAsync-0.0.1.jar"]
