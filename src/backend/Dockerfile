FROM ubuntu:focal
WORKDIR /backend
COPY . .
RUN ["chmod", "+x", "./install-packages.sh"]
RUN ./install-packages.sh
RUN mvn package
CMD ["mvn", "spring-boot:run", "-e"]
