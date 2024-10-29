
FROM openjdk:23-jdk-oracle

# Add Maintainer Info
LABEL maintainer="razvanmc15@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8010

# The application's jar file
ARG JAR_FILE=target/gym_management-1.0.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} gym_management.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egmvn d=file:/dev/./urandom","-jar","/gym_management.jar"]