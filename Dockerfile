FROM gradle:7.6-jdk17

WORKDIR /1x2network
COPY . .
RUN gradle bootRun

#CMD gradle spring-boot:run
EXPOSE 8080