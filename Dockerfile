FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} 16_6_BE-0.0.1-SNAPSHOT.jar
ENV TZ Asia/Seoul
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","/16_6_BE-0.0.1-SNAPSHOT.jar"]