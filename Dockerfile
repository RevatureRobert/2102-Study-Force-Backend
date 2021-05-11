ARG REPO=967240801169.dkr.ecr.us-east-1.amazonaws.com
FROM ${REPO}/java8:latest AS build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM gcr.io/distroless/java  
COPY --from=build /usr/src/app/target/StudyForce-0.0.1-SNAPSHOT.jar /usr/app/StudyForce-0.0.1-SNAPSHOT.jar 
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/StudyForce-0.0.1-SNAPSHOT.jar"]  
