FROM openjdk:24

ADD target/todocrud-0.0.1-SNAPSHOT.jar todo-app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/todo-app.jar"]