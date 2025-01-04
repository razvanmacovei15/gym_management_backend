FROM openjdk:21-jdk-oracle

# Add Maintainer Info
LABEL maintainer="razvanmc15@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8010 available to the world outside this container
EXPOSE 8010

# The application's jar file
ARG JAR_FILE=target/gym_management-1.0.0.jar

# Copy the application's jar to the container
COPY ${JAR_FILE} gym_management.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/gym_management.jar"]
